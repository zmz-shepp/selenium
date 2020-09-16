package com.testing.class4;

import com.testing.class3.ShopBuyTest;
import com.testing.webKeyword.DataDrivenOfWeb;
import com.testing.webKeyword.WebkeyWord;

public class PObuyAndAdd {
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        //首先实例化关键字类，也就是所有的页面都要用的BasePage
        WebkeyWord web=new WebkeyWord();
        String goodsName="第五课的测试商品2";
        //所有页面都使用同一个浏览器
        web.openBrowser("chrome");
        //测试后台登录功能
        ShopManagerTest.adminLogin(web,goodsName);
        //测试后台添加商品功能页面。
        ShopManagerTest.addGoods(web, goodsName);
        //断言添加商品是否成功
        web.assertPageContains(goodsName);
        //测试前台页面购买。
        ShopBuyTest.shopLogin(web);
        //前台页面中的购买测试
        ShopBuyTest.buyGoods(web);
        //断言购买是否成功
        web.assertEleContainsText("//div[@class='erhuh']/h3", "提交成功");
        web.closeBrowser();

    }




    }

