package com.testing.webKeyword;

import com.google.common.io.Files;
import com.testing.common.AutoLogger;
import com.testing.driverself.FFDriver;
import com.testing.driverself.GoogleDriver;
import com.testing.driverself.IEDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.GeckoDriverInfo;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.xml.xpath.XPath;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class WebkeyWord {

    // 成员变量webdriver，让每一个方法需要使用时都用同一个driver对象来进行操作，都在同一个浏览器中进行操作。
    // 避免外部程序对driver进行修改，可以声明为私有对象。
    public WebDriver driver = null;

    /**
     * 封装选择的浏览器
     *
     * @param type 浏览器类型
     */
    public void openBrowser(String type) {

        switch (type) {
            case "ie":
                IEDriver ie = new IEDriver("webDrivers/IEDriverServer.exe");
                driver = ie.getdriver();
                driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                break;
            case "chrome":
                GoogleDriver gg = new GoogleDriver("webDrivers/chromedriver.exe");
                driver = gg.getdriver();
                driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                break;
            case "Firefox":
                try {
                    System.setProperty("webdriver.gecko.driver", "webDrivers/geckodriver.exe");
                    driver = new FirefoxDriver();
                    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    AutoLogger.log.error(e, e.fillInStackTrace());
                    AutoLogger.log.error("创建火狐driver失败！！");
                }
                break;
            // 默认输入时用chrome进行启动


            default:
                GoogleDriver ggdefult = new GoogleDriver("webDrivers/chromedriver.exe");
                driver = ggdefult.getdriver();
                driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                break;
        }

    }







    /**
     * 访问网页的地址
     *
     * @param usl 网页地址
     */
    public void visitWeb(String usl) {
        driver.get(usl);
        try {
            AutoLogger.log.info("访问的地址是" + usl);

        } catch (Exception e) {
            AutoLogger.log.error(e, e.fillInStackTrace());

        }
    }


    /**
     * 基于name属性定位元素并且输入内容,然后提交。
     *
     * @param NameAttr   定位元素的name属性
     * @param inputContent 需要输入的内容
     * @param
     */
    public void inputAndSubmitByName(String NameAttr, String inputContent){
        try {
            WebElement element =driver.findElement(By.name(NameAttr));
            element.sendKeys(inputContent);
            element.submit();

        } catch (Exception e) {

            AutoLogger.log.error(e,e.fillInStackTrace());
        }
    }



    /**
     * 单击事件
     */
     public void click(String xpath){
        try {
            driver.findElement(By.xpath(xpath)).click();
            System.out.println("点击元素"+xpath);

        } catch (Exception e) {
            AutoLogger.log.error(e,e.fillInStackTrace());
        }

    }


    /**
     *
     */
    public void input(String xpath,String content) {
        try {
            WebElement ele=driver.findElement(By.xpath(xpath));
            //通过clear方法完成输入框中的清理
            ele.clear();
            ele.sendKeys(content);
            AutoLogger.log.info("向"+xpath+"元素输入"+content);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            AutoLogger.log.error("对"+xpath+"输入操作失败");
            AutoLogger.log.error(e,e.fillInStackTrace());
            saveScrShot("输入内容");
        }
    }



    /**
     * 通过findelements方法定位符合条件的所有元素
     * @param
     */
    void  getAllgoodsType(String xpath){
        try {
            AutoLogger.log.info("获取所有"+xpath+"元素的文本信息");
                List<WebElement> goodslist=driver.findElements(By.xpath(xpath));
                //遍历元素
                for (WebElement i:goodslist) {
                    System.out.println(i.getText());
                    System.out.println("大小" + i.getText());
                }
            } catch (Exception e) {
                AutoLogger.log.error(e,e.fillInStackTrace());
            }


        }



    /**
     *获取页面的标题
     *
     */
    public String gitTitle(){
        try {
                String Title=driver.getTitle();
                AutoLogger.log.error("标题是"+Title);
                return Title;
        }catch (Exception e){
                AutoLogger.log.error("获取标题失败");
                AutoLogger.log.error(e,e.fillInStackTrace());
                return "获取标题失败";
        }
    }



    /**
     * 显式等待，指定一个最长的等待时间，在这个时间内反复地确认预期的事件是否发生了，如果发生了，则结束等待，继续执行，如果超时还未发生，则报错。
     * 这个方法的实际用途是等待标题编程以cheese开头。
     *
     */
    public void explicitlyWaitTitle() {
        // 设定等待的事件，多少秒会超时。这里是10秒
        try {
            WebDriverWait ewait = new WebDriverWait(driver, 10);
            // 设定等待的事件是什么
            // 匿名内部类的声明
            // expectedcondition就是期望的等待事件，<Boolean>中的Boolean表示预期事件的类型。
            ewait.until(
                    // 完成等待事件的定义
                    new ExpectedCondition<Boolean>() {
                        // apply方法真正指定等待的条件。在等待过程中，会以一定的周期反复地观测事件是否达成。
                        // 如果返回为true，则等待成功，结束等待，如果为false，一直等满10秒之后，报错。
                        //这里的webdriver参数实际上由webdriverwait对象把webdriverwait实例化时用到的driver参数，作为实参传给形参roy。
                        public Boolean apply(WebDriver roy) {
                            // 返回一个Boolean类型的数据
                            // Boolean这个类型，在expectedcondition<>的尖括号里面定义的类型，同时也是apply方法要返回的类型。
                            return roy.getTitle().toLowerCase().startsWith("cheese!");
                        }
                    });
        } catch (Exception e) {
            // TODO Auto-generated catch block
            AutoLogger.log.error("显式等待标题变化失败");
            AutoLogger.log.error(e,e.fillInStackTrace());
        }
    }



    /**
     * 显式等待还可以使用selenium已经预定义好的一些等待条件。
     * @param xpathExp
     */
    public void explicitlyWaitEleLoc(String xpathExp) {
        try {
            WebDriverWait ewait =new WebDriverWait(driver, 10);
            //等待条件是指定的元素定位方法能够定位到的元素，出现了
            ewait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathExp)));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            AutoLogger.log.error("显式等待元素出现失败");
            AutoLogger.log.error(e,e.fillInStackTrace());
        }
    }




    /**
     * 线程休眠，最死板的等待，没有结束等待的条件，固定等待指定的时长。通常用于一些不确定原因的等待。
     */
    public void halt(String waitTime) {
        try {
            int t=Integer.parseInt(waitTime);
            //线程休眠，让程序停止一段时间，这个时间是固定的，没有任何条件来解除等待。
            //乘以1000转换为等待秒数
            Thread.sleep(t*1000);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            AutoLogger.log.error("强制等待异常");
            AutoLogger.log.error(e,e.fillInStackTrace());
        }
    }


    /**
     * 关闭浏览器以及driver进程
     * @param
     */
    public void closeBrowser(){
        try {
                driver.quit();
                AutoLogger.log.info("关闭浏览器");
        }catch (Exception e){
            AutoLogger.log.info("浏览器关闭异常");
            AutoLogger.log.error(e,e.fillInStackTrace());
        }
    }


    /**
     * 封装一个基于输入的用户名密码完成商城登录的方法
     * 用户名
     * @param  username     元素
     * @param  user         值
     *  密码
     * @param  password     元素
     * @param  pas           值
     */
    public void shopLogin(String username,String user,String password,String pas){


    }


    /**
     * 通过findelements方法定位符合条件的所有元素,获取其文本内容
     * @param
     */
    public void gitAllgoodsType(String xpath){
    List<WebElement> goodsslist=driver.findElements(By.xpath(xpath));
    for (WebElement e:goodsslist){
        System.out.println(e.getText());

    }
    }


    /**
     * 封装登录方法
     * @param username
     * @param password
     */
    public  void shopLogin(String username, String password){
        click("//a[text()='登录']");
        //定位账号元素，输入账号
        input("//input[@id='username']",username);
        //定位密码元素,输入密码
       input("//input[@id='password']", password);
        //定位验证码
        input("//input[@name='verify_code']","110");
        //登录按钮进入网页内部
        click("//a[@name='sbtbutton']");

    }


    /**
     * 将鼠标移动到某个元素上悬停
     * @param
     */
    public void hover(String xpath){
        try {
            Actions cat=new Actions(driver);
            cat.moveToElement(driver.findElement(By.xpath(xpath))).perform();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * 断言某个元素的某个属性的值符合预期
     * @param xpath
     * @param
     */
    public void assertEleAttr(String xpath,String exAttrkey,String exAttrValue) {
        WebElement ele=driver.findElement(By.xpath(xpath));
        //如果元素获取exAttrkey的值是Value则断言成功
        if(ele.getAttribute(exAttrkey).equals(exAttrValue)) {
            AutoLogger.log.info("测试成功");
        }
        else{
            AutoLogger.log.info("测试失败");
        }
    }




    /**
     * 断言页面中是否包含指定的内容。
     */
    public void assertPageContains(String exText) {
        String pageSource=driver.getPageSource();
        System.out.println(pageSource);
        if(pageSource.contains(exText)) {
            AutoLogger.log.info("测试成功");
        }
        else{
            AutoLogger.log.info("测试失败");
        }
    }



    /**
     * 断言，基于获取到的元素的文本内容，判断用例执行是否成功
     */
    public void assertEleContainsText(String xpath,String exText) {
        String eleText=driver.findElement(By.xpath(xpath)).getText();
        AutoLogger.log.info("断言元素的文本内容是："+eleText);
        if(eleText.contains(exText)) {
            AutoLogger.log.info("断言文本内容包含"+exText+"测试成功");
        }
        else {
            AutoLogger.log.info("断言文本内容包含"+exText+"测试失败");
        }
    }


    /**
     * 切换浏览器窗口的方法
     * @param  exTile 预期的浏览器窗口标题
     */
    public void switchWindowByTitle(String exTile){
         Set<String> handles = driver.getWindowHandles();

         String targetHandle="";

         //遍历所有的句柄,判断这个句柄对应的浏览器窗口标题是否是预期值
        for(String S:handles){

            //切换到各个窗口句柄,获取其标题,判断是否等于预期值
            if(driver.switchTo().window(S).getTitle().equals(exTile)){

                //如果是,则说明找到了需要切换窗口的句柄
                targetHandle=S;
                break;

            }
        }
        //切换到找到的目标句柄中
         //driver.switchTo().window(targetHandle);
    }



    public void  switchIframe(String Framename){
        try {
            driver.switchTo().frame(Framename);
            System.out.print("切换iframe成功");
            AutoLogger.log.info("切换成功");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("切换iframe失败");
        }
    }

    public void  switchIfranmeAsele(String xpath){
        try {
            WebElement frameElement=driver.findElement(By.xpath(xpath));
            driver.switchTo().frame(frameElement);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过js脚本拼接，实现滚动到指定的y坐标位置。
     * @param yAxis
     */

    public  void  runJs(String yAxis){
        try {
            JavascriptExecutor js=(JavascriptExecutor)driver;
            js.executeScript("window.scrollTo(0,"+yAxis+")");
            AutoLogger.log.info("执行js语句滚动到"+yAxis+"位置");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            AutoLogger.log.error("js语句执行失败");
            AutoLogger.log.error(e,e.fillInStackTrace());
        }
    }

    /**
     * 通过js滚动到最底端。
     */
    public void scrollToEnd() {
        try {
            JavascriptExecutor js=(JavascriptExecutor)driver;
            js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
            AutoLogger.log.info("执行js滚动条到底成功");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            AutoLogger.log.error("js语句执行失败");
            AutoLogger.log.error(e,e.fillInStackTrace());
        }
    }


    /**
     * 带参的js脚本调用，适用于通过xpath定位到元素然后调用js方法操作。
     * @param cmd  js脚本，使用arguments[0].click()这种方式进行参数的调用
     * @param xpath 传递的参数，通过xpath定位元素。
     */
    public void runJsWithArg(String cmd,String xpath) {
        try {
            //首先通过xpath定位到一个元素
            WebElement ele=driver.findElement(By.xpath(xpath));
            //然后再js执行器执行时，把元素作为参数穿进去，然后在js脚本里通过argument[0]来进行调用。
            JavascriptExecutor js=(JavascriptExecutor)driver;
            js.executeScript(cmd, ele);
            AutoLogger.log.info("对"+xpath+"元素执行js语句操作");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            AutoLogger.log.error("js语句执行失败");
            AutoLogger.log.error(e,e.fillInStackTrace());
        }
    }



    public void selectByText(String xpath ,String visibleText){
        try {
            //通过select类的构造方法,完成将元素转换成select对象过程
            Select sel=driver.findElement(By.xpath(xpath));
            sel.selectByVisibleText(visibleText);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *  通过select元素中的option选项的value属性进行选择
     * @param xpath
     * @param optionValue
     */

    public void  selectByValue(String xpath,String optionValue){
        try {
            //通过select类的构造方法,完成将元素转换成select对象的过程
            Select sel=new Select(driver.findElement(By.xpath(xpath)));
            sel.selectByValue(optionValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void   uploadFile(String xpath,String FilePath){
        input(xpath,FilePath);
    }



    public String getJsReturnWithArg(String cmd,String xpath) {
        try {
            WebElement ele=driver.findElement(By.xpath(xpath));
            //然后再js执行器执行时，把元素作为参数穿进去，然后在js脚本里通过argument[0]来进行调用。
            JavascriptExecutor js=(JavascriptExecutor)driver;
            //在执行js脚本时，加上return，将结果进行返回
            String result=js.executeScript("return "+cmd, ele).toString();
            AutoLogger.log.info("对"+xpath+"元素执行js语句操作，返回结果是"+result);
            return result;
        } catch (Exception e) {
            AutoLogger.log.error("js语句执行失败");
            AutoLogger.log.error(e,e.fillInStackTrace());
            return "js语句执行失败";
        }
    }




    /**
     * 切入iframe之后，调用该方法，切回最外层的html
     */
    public void switchToRoot() {
        try {
            driver.switchTo().defaultContent();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            AutoLogger.log.error("切换到网页最外层失败");
            AutoLogger.log.error(e,e.fillInStackTrace());
        }
    }





    public void saveScrShot(String method) {
        // 获取当前的执行时间
        Date date = new Date();
        //将时间转换为指定的格式,+号-号这些分隔符可以自己随便替换成自己喜欢的   2020/2/10 22:07  20200210+22-07-33
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd+HH-mm-ss");
        // 将当前时间转换为上一步指定格式的字符串
        String createdate = sdf.format(date);
        // 拼接文件名，形式为：工作目录路径+方法名+执行时间.png
        String scrName = "log/ScrShot/" + method + createdate + ".png";

        // 以当前文件名创建一个空的png文件
        File scrShot = new File(scrName);
        // 将截图保存到内存中的临时文件
        File tmp = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        //将内存中保存的截图复制到创建出来的png文件中。
        try {
            Files.copy(tmp, scrShot);
            AutoLogger.log.info("错误截图保存在"+scrName);
        } catch (IOException e) {
            e.printStackTrace();
            AutoLogger.log.error("截图失败");
        }
    }




    public void switchIframeAsele(String xpath) {
        try {
            WebElement frameElement =driver.findElement(By.xpath(xpath));
            driver.switchTo().frame(frameElement);
            AutoLogger.log.info("切换"+xpath+"iframe成功");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            AutoLogger.log.error("切换到"+xpath+"iframe失败");
            AutoLogger.log.error(e,e.fillInStackTrace());
        }
    }

}
