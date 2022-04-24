package indi.hadeslock.server.controller;

import indi.hadeslock.server.model.pojo.Record;
import indi.hadeslock.server.model.pojo.RespBean;
import indi.hadeslock.server.service.IRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/record")
@Api(tags = "测试记录操作")
public class RecordController {
    @Autowired
    private IRecordService recordService;

    @ApiOperation(value = "上传测试记录")
    @PostMapping("/upload")
    public RespBean uploadRecord(MultipartFile file) {
        if (file==null || file.getSize() == 0) {
            return  RespBean.error("文件异常");
        }

        //实体的集合，把csv中的列装在list里。
        List<Record> list = new ArrayList<>();
        //保存设备地址、病人id、开始时间、结束时间等杂项信息
        HashMap<String, String> dataMap = new HashMap<>();
        //获取文件名
        String fileName = file.getOriginalFilename();
        //获取文件后缀
        assert fileName != null;
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        //日期解析器
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        //只对csv文件进行解析
        if (suffix.equals(".csv")) {
            //定义一些流
            InputStream inputStream = null;
            InputStreamReader is = null;
            BufferedReader reader = null;
            try {
                //初始化流
                inputStream = file.getInputStream();
                is = new InputStreamReader(inputStream, "GBK"); //编码格式要用GBK
                reader = new BufferedReader(is);
                //处理设备和病人的信息
                String line;
                line = reader.readLine();  //第一行杂项信息
                String[] splitData = line.split(",");
                for (int i = 0; i*2 < splitData.length; i++) {
                    dataMap.put(splitData[2*i], splitData[2*i+1]);
                }
                //处理设备测量数据
                while ((line = reader.readLine()) != null) {
                    //实体类
                    Record record = new Record();
                    String[] val = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分
                    //恢复实体类，这里其实应该做更多的校验处理
                    try {
                        java.util.Date date = dateFormat.parse(val[0]);
                        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                        record.setDate(sqlDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    record.setTime(Time.valueOf(val[1]));
                    record.setPotential(Double.parseDouble(val[2]));
                    list.add(record);
                }
                //处理日期、开始测量时间和结束测量时间
                int n = list.size();
                dataMap.put("date", list.get(0).getDate().toString());
                dataMap.put("startTime", list.get(0).getTime().toString());
                dataMap.put("endTime", list.get(n-1).getTime().toString());
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    if (is != null) {
                        is.close();
                    }
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //保存记录信息
            if(recordService.saveRecords(dataMap, list) == 1){
                return RespBean.success("上传失败，时间段内已有记录，请勿重复添加");
            }
        }else {
            return RespBean.error("上传文件格式错误");
        }
        return RespBean.success("导入成功");
    }

}
