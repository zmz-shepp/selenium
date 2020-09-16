package com.testing.class3;

import com.testing.webKeyword.WebkeyWord;

public class ShopBuyTest {
    public static void main(String[] args) {
        WebkeyWord web=new WebkeyWord();



    }
    public static void shopLogin(WebkeyWord web) {
        web.visitWeb("http://www.testingedu.com.cn:8000/");
        //登录
        web.shopLogin("13800138006", "123456");
    }

    public static  void  buyGoods(WebkeyWord web){
        web.openBrowser("Chrome");

        web.visitWeb("http://www.testingedu.com.cn:8000/");

        //登录封装的方法
        web.shopLogin("13800138006","123456");

        //点击返回商品首页
        web.click("//a[text()='返回商城首页']");

        //悬停到全部商品分类
        web.hover("//a[text()='全部商品分类']");

        //悬停到手机数码
        web.hover("//a[text()='手机数码']");

        web.click("//a[text()='手机' and not(@class)]");

        //切换浏览器窗口
        web.switchWindowByTitle("商品列表");


        //点击第二个商品
        web.click("//div[@class='shop-list-splb p']/ul/li[2]//div[@class='shop_name2']/a");
        web.click("//a[@id='join_cart']");

        //由于id和name中包含了一个自动生成的编号,所以id和name是变化的,选择不变的父元素来辅助定位ifrname元素,进行切换。
        web.switchIfranmeAsele("//div[@class='layui-layer-content']/iframe");

        web.halt("5");

        web.click("//a[text()='去购物车结算']");

        //点击去结算
        web.click("//a[@class='paytotal']");

        //滚动滑动条到最下
        web.scrollToEnd();

        //等候收货地址加载完成
        web.halt("3");


        //基于传参的方式,先通过xpath定位到元素,然后用arguments[0]调用,进行js脚本执行
        web.runJsWithArg("arguments[0].click()","//button[@type='submit']");

        //断言订单提交成功
        web.assertEleContainsText("//div[@class='erhuh']/h3","提交成功");

    }



    //针对于首页这个页面进行的测试，所有的需要的定位元素和操作方法都在这。
    public static void adminLogin(WebkeyWord web) {
        web.visitWeb("http://www.testingedu.com.cn:8000/Admin/Admin/login");
        web.input("//input[@name='username']", "admin");
        web.input("//input[@name='password']", "123456");
        //验证码随意输
        web.input("//input[@name='vertify']", "126");
        web.click("//input[@name='submit']");
    }

}
