package com.testing.class2;

import com.testing.webKeyword.WebkeyWord;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest {


           WebkeyWord web=new WebkeyWord();






       
//       @BeforeMethod
//       public void dengl(){
//              web.openBrowser("Chrome");
//              web.visitWeb("http://121.199.74.159:8080/shiro/login");
//
//              web.input("//input[@name='username']","admin");
//              web.input("//input[@name='password']","123");
//              web.click("//a[text()='登录']");
//
//
//              web.gitAllgoodsType("//div[@id='sidebar-menu']");
//
//              //点击系统管理
//              web.click("//a[text()='系统管理']");
//
//              web.halt("1");
//
//              //调用循环遍历方法
//              //系统管理下的子菜单不显示用户管
//              web.gitAllgoodsType("//div[@id='sidebar-menu']//li[@class='active']//ul[@class='nav child_menu']//li[not(a='用户管理')]");
//
//              //文件上传
//              web.click("//div[@id='sidebar-menu']//li[@class='active']//ul[@class='nav child_menu']//li[5]");
//              web.switchIframe("workspace");
//              web.halt("2");
//              //切换到iframe
//
//              web.switchIfranmeAsele("//iframe[@name='main-body']");
//       }



       @Test
       public void  testt(){

              /**
               * 特撕钉开源商城
               */

              web.openBrowser("Chrome");
              //网页地址
              web.visitWeb("http://www.testingedu.com.cn:8000/");
              //输出元素的集合
              web.gitAllgoodsType("//div[@class='cata-nav-wrap']/a");
              //网页内部点击登录进入首页
              web.click("//a[text()='登录']");
              //定位账号元素，输入账号
              web.input("//input[@id='username']", "13800138006");
              //定位密码元素,输入密码
              web.input("//input[@id='password']", "123456");
              //定位验证码
              web.input("//input[@name='verify_code']","110");
              //登录按钮进入网页内部
              web.click("//a[@name='sbtbutton']");
              //安全退出（退出账号）
              web.click("//a[text()='安全退出']");
              //网页内部点击注册
              web.click("//a[text()='注册']");
              //我已注册，马上登录
              //跳转首页
              web.click("//a[text()='登录>']");


              web.openBrowser("Chrome");
              web.visitWeb("http://www.testingedu.com.cn:8000/Home/User/index.html");
              //输入所有商品列表中的内容
              web.gitAllgoodsType("//div[@class='cat-nav-wrap']/a");
       }




       @BeforeMethod
     public  void da(){
           web.openBrowser("Chrome");
           web.visitWeb("http://www.testingedu.com.cn:8000/Admin/Admin/login");
           web.input("//input[@name='username']","admin");
           web.input("//input[@name='password']","123456");
           web.input("//input[@name='vertify']","126");
           web.click("//input[@name='submit']");
           web.halt("3");

           web.click("//a[text()='商城']");
           //切换到iframe中
           web.switchIframe("workspace");

           web.click("//span[text()='添加商品']");

           web.input("//input[@name='goods_name']","VIP张慕卓的测试商品");
           web.selectByText("(//select)[1]","电脑");
           //由于选择第一个下拉框之后,后面的下拉框需要时间来相应变化,但是select元素一直能被定位到,所以不会触发隐式等待,因此使用强制等
           web.halt("1");
           web.selectByText("(//select)[2]","电脑整机");
           web.halt("1");
           web.selectByText("(//select)[3]","游戏本");
           web.input("//input[@name='shop_price']", "5000");
           web.input("//input[@name='market_price']","5500");
           //文件上传先点击按钮,弹出iframe
           web.click("//input[contains(@title,'查看大图')]");
           //切换到ifrmae中进行操作
           web.switchIfranmeAsele("//iframe[contains(@src,'func=img')]");
           //文件上传
           web.halt("4");
           web.uploadFile("//div[@id='filePicker']//input[@type='file']","E:\\2222233.png");


           web.click("//div[@class='saveBtn']");
           //弹窗操作完成了，切回主html页面层级。
           web.switchToRoot();
           web.switchIframe("workspace");
           web.click("//label[contains(string(),'是否包邮')]/../following-sibling::dd//label[text()='是']");
           //切换到富文本框的iframe中
           web.switchIframe("ueditor_0");
           //通过js往富文本框中输入内容
           String jsresult=web.getJsReturnWithArg("arguments[0].innerText=\"这是VIP05的测试商品\"", "//p");
           System.out.println(jsresult);
           //切回iframe
           web.switchToRoot();
           web.switchIframe("workspace");
           web.click("//a[@id='submit']");
           web.closeBrowser();

       }
}

