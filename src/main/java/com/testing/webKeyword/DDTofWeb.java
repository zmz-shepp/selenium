package com.testing.webKeyword;

import com.testing.common.AutoLogger;
import com.testing.common.ExcelReader;
import com.testing.common.ExcelWriter;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DDTofWeb {

    private static DataDrivenOfWeb web;



    @Test
    public void shou(){
            //前期准备工作，完成excel的读写类的实例化。并且创建好结果文件。
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HH+mm+ss");
            String createdate = sdf.format(date);
            // 创建excelreader对象，打开用例源文件
            ExcelReader oriCase = new ExcelReader("E:\\selenium\\case\\工作簿1.xlsx");
            // 创建excelWriter对象，复制一份结果文件，并且打开它进行操作
            ExcelWriter resCase = new ExcelWriter("E:\\selenium\\case\\工作簿1.xlsx", "E:\\selenium\\case\\xianmu" + createdate + ".xlsx");

            //创建一下关键字对象
            web = new DataDrivenOfWeb(resCase);

            //真正执行用例：
            //遍历excel用例文件
            //遍历sheet页
            for (int sheetIndex = 0; sheetIndex < oriCase.getTotalSheetNo(); sheetIndex++) {
                //指定使用当前序号的sheet页
                oriCase.useSheetByIndex(sheetIndex);
                resCase.useSheetByIndex(sheetIndex);
                AutoLogger.log.info("+6++++++++++++++++++++当前的sheet页是：" + oriCase.getSheetName(sheetIndex) + "+++++++++++++++++++++++++++++++");
                //sheet页中对于每一行进行遍历
                //读取用例每一行中的内容为list<String>，然后对于这个list来进行操作，识别关键字，传递参数
                List<String> rowContent = new ArrayList<String>();
                for (int rowNo = 0; rowNo < oriCase.rows; rowNo++) {
                    //读取正在遍历的行。
                    rowContent = oriCase.readLine(rowNo);
                    //通过反射机制进行调用
                    //invokeWebkey(rowContent);

//				//基于第四列的内容，也就是下标为3的列，判断关键字调用。
                    switch (rowContent.get(1)) {

                        case "openBrowser":
                            web.openBrowser(rowContent.get(2));

                            break;


                        case "visitWeb":
                            web.visitWeb(rowContent.get(2));
                            break;

                        case "input":
                            web.inputXpath(rowContent.get(2), rowContent.get(3));

                            break;

                        case "hover":
                            web.Hover(rowContent.get(2));
                            break;

                        case "halt":
                            web.halt(rowContent.get(2));
                            break;

                        case "click":
                            web.click(rowContent.get(2));
                            break;



//				//断言操作也是一个独立的关键字进行调用，这里和接口不同。
                        case "assertSame":
                            web.assertContentIs(rowContent.get(3), rowContent.get(4));
                            break;
                    }
                }
                //记得保存excel文件
                resCase.save();

            }
        }







    /**
     * 基于方法名，在类中找对应的方法，使用trycatch机制，尝试各个长度的参数列表进行匹配。
     *
     * @param line
     */
    private static void invokeWebkey(List<String> line) {
        //基于方法名获取参数为空的方法
        try {
            //获取对象对应的类中所有的方法。
            Method appMethod = web.getClass().getDeclaredMethod(line.get(3).toString());
            // invoke语法，需要输入类名以及相应的方法用到的参数
            appMethod.invoke(web);
            return;
        } catch (Exception e) {
        }
        //基于方法名获取带有一个参数的方法
        try {
            Method appMethod = web.getClass().getDeclaredMethod(line.get(3).toString(), String.class);
            // invoke语法，需要输入类名以及相应的方法用到的参数
            appMethod.invoke(web, line.get(4));
            return;
        } catch (Exception e) {
        }
        //基于方法名获取带有两个参数的方法
        try {
            Method appMethod = web.getClass().getDeclaredMethod(line.get(3).toString(), String.class, String.class);
            // invoke语法，需要输入类名以及相应的方法用到的参数
            appMethod.invoke(web, line.get(4), line.get(5));
            return;
        } catch (Exception e) {
        }
        //基于方法名获取带有三个参数的方法
        try {
            Method appMethod = web.getClass().getDeclaredMethod(line.get(3).toString(), String.class, String.class,
                    String.class);
            // invoke语法，需要输入类名以及相应的方法用到的参数
            appMethod.invoke(web, line.get(4), line.get(5), line.get(6));
            return;
        } catch (Exception e) {
        }
    }
}
