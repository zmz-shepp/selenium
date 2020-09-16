package com.testing.class4;

import com.testing.webKeyword.WebkeyWord;

public class ShopManagerTest {
    public static void main(String[] args) {
        WebkeyWord web=new WebkeyWord();
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

    public static void adminLogin(WebkeyWord web, String goodsname) {
        web.visitWeb("http://www.testingedu.com.cn:8000/Admin/Admin/login");
        web.input("//input[@name='username']", "admin");
        web.input("//input[@name='password']", "123456");
        //验证码随意输
        web.input("//input[@name='vertify']", "126");
        web.click("//input[@name='submit']");
    }



    public static void addGoods(WebkeyWord web,String goodsName) {
        web.switchIframe("workspace");
        web.click("//span[text()='添加商品']");
        web.halt("5");
        web.input("//input[@name='goods_name']", goodsName);
        web.selectByText("(//select)[1]", "电脑");
        //由于选择第一个下拉框之后，后面的下拉框需要时间来相应变化，但是select元素一直能被定位到，所以不会触发隐式等待，因此使用强制等待
        web.halt("2");
        web.selectByText("(//select)[2]", "电脑整机");
        web.halt("2");
        web.selectByText("(//select)[3]", "游戏本");
        web.input("//input[@name='shop_price']", "5000");
        web.input("//input[@name='market_price']", "5500");
        //文件上传先点击按钮,弹出iframe
        web.click("//input[contains(@title,'查看大图')]");
        //切换到ifrmae中进行操作
        web.switchIframeAsele("//iframe[contains(@src,'func=img')]");
        //文件上传
        web.halt("5");
        web.uploadFile("//div[@id='filePicker']//input[@type='file']", "F:\\test.jpg");
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
    }
}
