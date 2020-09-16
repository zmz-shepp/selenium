package com.testing.webKeyword;

import com.google.common.io.Files;
import com.testing.common.AutoLogger;
import com.testing.common.ExcelWriter;
import com.testing.driverself.FFDriver;
import com.testing.driverself.GoogleDriver;
import com.testing.driverself.IEDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class DataDrivenOfWeb {
	// 成员变量webdrvier对象driver，用这个成员变量操作后续的所有方法。
	public WebDriver driver = null;

	// 声明写excel对象。
	public ExcelWriter webExcel = null;
	// 当前行数的成员变量
	public int line = 0;

	public DataDrivenOfWeb(ExcelWriter excel) {
		webExcel = excel;
	}
	/**
	 * 在关键字类中完成excelwriter的实例化。
	 * 那么现需要关键字类中有个保存的方法。
	 * @param caseFile
	 * @param resultFile
	 */
	public DataDrivenOfWeb(String caseFile, String resultFile) {
		webExcel=new ExcelWriter(caseFile, resultFile);
	}
	
	
	public void setline(int rowNo) {
		line=rowNo;
	}
	//保存excel的方法写进关键字类。
	public void saveExcel() {
		webExcel.save();
	}
	
	/**
	 * 启动浏览器的方法
	 * 
	 * @param浏览器的类型：可以是IE/FF/chrome
	 */
	public void openBrowser(String browserType) {
		try {
			switch (browserType) {
			case "chrome":
				GoogleDriver gg = new GoogleDriver("webDrivers\\chromedriver.exe");
				driver = gg.getdriver();
				AutoLogger.log.info("chrome浏览器启动");
				break;
			case "FF":
				FFDriver ff = new FFDriver("E:\\Program Files\\Mozilla Firefox\\firefox.exe", "webDrivers/geckodriver.exe");
				driver = ff.getdriver();
				break;
			case "IE":
				IEDriver IE = new IEDriver("webDrivers\\IEDriver.exe");
				driver = IE.getdriver();
				break;
			default:
				GoogleDriver google = new GoogleDriver("webDrivers\\IEDriver.exe");
				driver = google.getdriver();
			}
			webExcel.writeCell(line, 10, "PASS");
		} catch (Exception e) {
			webExcel.writeFailCell(line, 10, "FAIL");
			AutoLogger.log.error(e, e.fillInStackTrace());
		}





	}








	/**
	 *

	 * 调整浏览器窗口大小
	 */
	public void setWindow() {
		try {
			Point p = new Point(320, 0);
			Dimension d = new Dimension(1400, 1000);
			driver.manage().window().setPosition(p);
			driver.manage().window().setSize(d);
			AutoLogger.log.info("调整浏览器窗口大小为1400*1000");
			webExcel.writeCell(line, 10, "PASS");
		} catch (Exception e) {
			webExcel.writeFailCell(line, 10, "FAIL");
		}
	}

	/**
	 * 访问URL的方法
	 * 
	 * @param参数为网页URL
	 */
	public void visitWeb(String URL) {
		try {
			driver.get(URL);
			AutoLogger.log.info("访问" + URL);
			webExcel.writeCell(line, 10, "PASS");

		} catch (Exception e) {
			webExcel.writeFailCell(line, 10, "FAIL");
			AutoLogger.log.error(e, e.fillInStackTrace());
			System.out.println("访问失败");
		}
	}

	/**
	 * 定位输入框并输入内容方法
	 * 
	 * @param
	 */
	public void inputXpath(String xpath, String content) {
		try {
			explicitlyWait(xpath);
			WebElement element = driver.findElement(By.xpath(xpath));
			element.clear();
			element.sendKeys(content);
			webExcel.writeCell(line, 10, "PASS");
			AutoLogger.log.info("点击输入——"+content);
		} catch (Exception e) {
			AutoLogger.log.error(e, e.fillInStackTrace());
			saveScrShot("input");
			webExcel.writeFailCell(line, 10, "FAIL");
			AutoLogger.log.info("input定位失败");
		}
	}



	public void inputCss(String css, String content) {
		try {
			explicitlyWait(css);
			WebElement element = driver.findElement(By.className(css));
			element.clear();
			element.sendKeys(content);
			webExcel.writeCell(line, 10, "PASS");
		} catch (Exception e) {
			AutoLogger.log.error(e, e.fillInStackTrace());
			saveScrShot("input");
			webExcel.writeFailCell(line, 10, "FAIL");
		}
	}












	public void click(String xpath) {
		try {
			explicitlyWait(xpath);
			WebElement element = driver.findElement(By.xpath(xpath));
			element.click();
			webExcel.writeCell(line, 10, "PASS");
		} catch (Exception e) {
			AutoLogger.log.error(e, e.fillInStackTrace());
			saveScrShot("click");
			webExcel.writeFailCell(line, 10, "FAIL");
		}
	}

	/**
	 * 使用actions类，调用moveElement方法实现悬停。
	 * 
	 * @param xpath
	 */
	public void hover(String xpath) {
		try {
			WebElement element = driver.findElement(By.xpath(xpath));
			Actions act = new Actions(driver);
			act.moveToElement(element).build().perform();
			AutoLogger.log.info("悬停到指定元素");
			webExcel.writeCell(line, 10, "PASS");
		} catch (Exception e) {
			AutoLogger.log.error(e, e.fillInStackTrace());
			AutoLogger.log.info("元素悬停失败！");
			saveScrShot("hover");
			webExcel.writeFailCell(line, 10, "FAIL");
			webExcel.writeFailCell(line, 11, e.fillInStackTrace().toString());
		}
	}

	/**
	 * 实现显式等待的方法，在每次定位元素时，先尝试找元素，给10秒钟的最长等待。
	 */
	public void explicitlyWait(String xpath) {
		try {
			WebDriverWait ewait = new WebDriverWait(driver, 10);
			// 设置等待的预期条件为，元素可以被定位到。
			ewait.until(new ExpectedCondition<WebElement>() {
				public WebElement apply(WebDriver d) {
					return d.findElement(By.xpath(xpath));
				}
			});
		} catch (Exception e) {
			AutoLogger.log.error(e, e.fillInStackTrace());
		}
	}

	/**
	 * 实现获取页面标题的方法
	 */
	public String getTitle() {
		String title = "";
		try {
			title = driver.getTitle();
			return title;
		} catch (Exception e) {
			AutoLogger.log.error(e, e.fillInStackTrace());
			AutoLogger.log.info("获取页面标题失败！");
			return null;
		}

	}

	/**
	 * 实现关闭浏览器的方法
	 */
	public void closeBrowser() {
		try {
			driver.quit();
			driver = null;
			webExcel.writeCell(line, 10, "PASS");
		} catch (Exception e) {
			webExcel.writeFailCell(line, 10, "FAIL");
			AutoLogger.log.error(e, e.fillInStackTrace());
		}
	}

	/**
	 * 实现隐式等待的方法
	 */
	public void implicitlyWait() {
		try {
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			webExcel.writeCell(line, 10, "PASS");
		} catch (Exception e) {
			AutoLogger.log.error(e, e.fillInStackTrace());
			webExcel.writeFailCell(line, 10, "FAIL");
		}
	}

	/**
	 * 强制等待的方法
	 * 
	 * @param
	 //t强制等待的秒数，用字符串类型传递
	 */
	public void halt(String t) {
		try {
			int time = Integer.parseInt(t);
			Thread.sleep(time * 1000);
			webExcel.writeCell(line, 10, "PASS");
		} catch (InterruptedException e) {
			AutoLogger.log.error(e, e.fillInStackTrace());
			webExcel.writeFailCell(line, 10, "FAIL");
		}
	}

	// 进入iframe子页面
	public void intoIframe(String xpath) {
		try {
			explicitlyWait(xpath);
			WebElement frame = driver.findElement(By.xpath(xpath));
			driver.switchTo().frame(frame);
			AutoLogger.log.info("进入iframe成功！");
			webExcel.writeCell(line, 10, "PASS");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			AutoLogger.log.error(e, e.fillInStackTrace());
			AutoLogger.log.info("进入iframe失败");
			saveScrShot("切入Iframe");
			webExcel.writeFailCell(line, 10, "FAIL");
		}
	}

	// 退出子页面
	public void outIframe() {
		try {
			// 切回主页面
			driver.switchTo().defaultContent();
			webExcel.writeCell(line, 10, "PASS");
		} catch (Exception e) {
			AutoLogger.log.error(e, e.fillInStackTrace());
			AutoLogger.log.error("切出Iframe失败");
			saveScrShot("切出Iframe");
			webExcel.writeFailCell(line, 10, "FAIL");
		}
	}

	// 通过窗口标题切换窗口
	public void switchWindow(String target) {
		// 创建一个字符串便于之后存放句柄
		String s = null;
		// 获取当前页面中的句柄
		Set<String> handles = driver.getWindowHandles();
		// 循环尝试，找到目标浏览器页面的句柄
		for (String t : handles) {
			// 遍历每一个句柄，判断窗口的标题是否包含预期字符
//					System.out.println(t);
			if (driver.switchTo().window(t).getTitle().equals(target)) {
				s = t;
			}
		}
		// 切换到目标句柄的页面中
		try {
			driver.switchTo().window(s);
			webExcel.writeCell(line, 10, "PASS");
		} catch (Exception e) {
			AutoLogger.log.error(e, e.fillInStackTrace());
			AutoLogger.log.info("通过页面标题切换窗口失败！");
			webExcel.writeFailCell(line, 10, "FAIL");
		}
	}

	// 关闭旧窗口，切换到新窗口操作
	public void closeOldWin() {
		List<String> handlelist = new ArrayList<String>();
		// 返回一个句柄集合
		Set<String> handles = driver.getWindowHandles();
		// 循环获取集合里面的句柄，保存到List数组handles里面
		Iterator<String> it = handles.iterator();
		while (it.hasNext()) {
			handlelist.add(it.next().toString());
		}
		// 关闭第一个窗口
		driver.close();
		// 切换到新窗口
		try {
			driver.switchTo().window(handlelist.get(1));
			webExcel.writeCell(line, 10, "PASS");
		} catch (Exception e) {
			AutoLogger.log.error(e, e.fillInStackTrace());
			AutoLogger.log.info("关闭旧窗口切换到新窗口失败");
			webExcel.writeFailCell(line, 10, "FAIL");
		}
	}

	// 关闭新窗口
	public void closeNewWin() {
		List<String> handles = new ArrayList<String>();
		Set<String> s = driver.getWindowHandles();
		// 循环获取集合里面的句柄，保存到List数组handles里面
		for (Iterator<String> it = s.iterator(); it.hasNext();) {
			handles.add(it.next().toString());
		}
		try {
			driver.switchTo().window(handles.get(1));
			driver.close();
			driver.switchTo().window(handles.get(0));
			webExcel.writeCell(line, 10, "PASS");
		} catch (Exception e) {
			AutoLogger.log.error(e, e.fillInStackTrace());
			AutoLogger.log.error("关闭新窗口失败！");
			webExcel.writeFailCell(line, 10, "FAIL");
		}
	}

	// 获取js运行结果
	public String getJs(String text) {
		String t = "";
		JavascriptExecutor js = (JavascriptExecutor) driver;
		try {
			t = js.executeScript("return " + text).toString();
			webExcel.writeCell(line, 10, "PASS");
		} catch (Exception e) {
			AutoLogger.log.error(e, e.fillInStackTrace());
			AutoLogger.log.error("JS脚本执行失败！");
			webExcel.writeFailCell(line, 10, "FAIL");
		}
		return t;
	}

	// 执行无返回的js脚本
	public void runJs(String text) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		try {
			js.executeScript(text);
			webExcel.writeCell(line, 10, "PASS");
		} catch (Exception e) {
			AutoLogger.log.error(e, e.fillInStackTrace());
			AutoLogger.log.error("JS脚本执行失败！");
			webExcel.writeFailCell(line, 10, "FAIL");
		}
	}

	// 执行浏览器滚动
	public void scrollWindowStraight(String height) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		try {
			String jsCmd = "window.scrollTo(0," + height + ")";
			js.executeScript(jsCmd);
			webExcel.writeFailCell(line, 10, "PASS");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			AutoLogger.log.error(e, e.fillInStackTrace());
			AutoLogger.log.error("操作浏览器滚动条失败");
			saveScrShot("scroll");
			webExcel.writeFailCell(line, 10, "FAIL");
		}
	}

	// 实现select方法
	public void selectByText(String xpath, String text) {
		try {
			// 将webelement转换为select
			Select userSelect = new Select(driver.findElement(By.xpath(xpath)));
			userSelect.selectByVisibleText(text);
			webExcel.writeCell(line, 10, "PASS");
		} catch (Exception e) {
			AutoLogger.log.error(e, e.fillInStackTrace());
			AutoLogger.log.error("通过文本选择Select失败！");
			saveScrShot("Select");
			webExcel.writeFailCell(line, 10, "FAIL");
		}
	}

	// 实现通过value选择select的选项
	public void selectByValue(String xpath, String value) {
		try {
			// 将webelement转换为select
			Select userSelect = new Select(driver.findElement(By.xpath(xpath)));
			userSelect.selectByValue(value);
			webExcel.writeCell(line, 10, "PASS");
		} catch (Exception e) {
			AutoLogger.log.error(e, e.fillInStackTrace());
			AutoLogger.log.error("通过值选择Select失败！");
			saveScrShot("Select");
			webExcel.writeFailCell(line, 10, "FAIL");
		}
	}

	// 封装关键字实现登录
	public void login(String username, String password) {
		try {
			visitWeb("http://112.74.191.10:8000/");
			click("//a[@class='red']");
			inputXpath("//input[@id='username']", username);
			inputXpath("//input[@id='password']", password);
			inputXpath("//input[@placeholder='验证码']", "1");
			click("//a[@name='sbtbutton']");
			webExcel.writeCell(line, 10, "PASS");
		} catch (Exception e) {
			webExcel.writeFailCell(line, 10, "FAIL");
		}
	}

	// 报错时截图
	public void saveScrShot(String method) {
		// 获取当前的执行时间
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd+HH-mm-ss");
		// 当前时间的字符串
		String createdate = sdf.format(date);
		// 拼接文件名，形式为：工作目录路径+方法名+执行时间.png
		String scrName = "SCRshot/" + method + createdate + ".png";
		// 以当前文件名创建文件
		File scrShot = new File(scrName);
		// 将截图保存到临时文件
		File tmp = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			Files.copy(tmp, scrShot);
		} catch (IOException e) {
			AutoLogger.log.error(e, e.fillInStackTrace());
			AutoLogger.log.error("截图失败！");
		}
	}

	/**
	 * 断言标题中包含指定内容
	 * 
	 * @param target 标题包含的内容
	 */
	public void assertTitleContains(String target) {
		String result = getTitle();
		if (result.contains(target)) {
			AutoLogger.log.info("测试成功！");
			webExcel.writeCell(line, 10, "PASS");
		} else {
			AutoLogger.log.info("测试失败！");
			webExcel.writeFailCell(line, 10, "FAIL");
		}
	}

	/**
	 * 断言标题的内容符合预期
	 * 
	 * @param target 标题内容
	 */
	public void assertTitleIs(String target) {
		String result = getTitle();
		if (result.equals(target)) {
			AutoLogger.log.info("测试成功！");
			webExcel.writeFailCell(line, 10, "PASS");
		} else {
			AutoLogger.log.info("测试失败！");
			webExcel.writeFailCell(line, 10, "FAIL");
		}
	}

	/**
	 * 断言页面中某个元素的文本内容是否符合预期
	 * 
	 * @param xpath  元素定位xpath表达式
	 * @param target 预期的内容
	 */
	public void assertContentIs(String xpath, String target) {
		try {
			WebElement ele = driver.findElement(By.xpath(xpath));
			String text = ele.getText();
			if (text.equals(target)) {
				AutoLogger.log.info("测试成功！");
				webExcel.writeCell(line, 10, "PASS");
			} else {
				AutoLogger.log.info("测试失败！");
				webExcel.writeFailCell(line, 10, "FAIL");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			AutoLogger.log.info("未找到指定元素！");
		}
	}

	/**
	 * 断言元素的某个值是否符合预期
	 * 
	 * @param xpath  元素定位xpath表达式
	 * @param attr   元素中的属性
	 * @param target 属性值
	 */
	public void assertAttrIs(String xpath, String attr, String target) {
		try {
			WebElement ele = driver.findElement(By.xpath(xpath));
			String value = ele.getAttribute(attr);
			if (value.equals(target)) {
				AutoLogger.log.info("测试成功！");
				webExcel.writeCell(line, 10, "PASS");
			} else {
				AutoLogger.log.info("测试失败！");
				webExcel.writeFailCell(line, 10, "FAIL");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			AutoLogger.log.info("未找到指定元素的指定属性！");
		}
	}

	/**
	 * 断言页面源码中包含某个内容
	 * 
	 * @param target 页面中预期包含的内容
	 */
	public void assertPageContains(String target) {
		String pageContent = driver.getPageSource();
		if (pageContent.contains(target)) {
			AutoLogger.log.info("测试成功！");
			webExcel.writeCell(line, 10, "PASS");
		} else {
			AutoLogger.log.info("测试失败！");
			webExcel.writeFailCell(line, 10, "FAIL");
		}
	}



	/**
	 * 将鼠标移动到某个元素上悬停
	 * @param
	 */
	/**
	 * 将鼠标移动到某个元素上悬停
	 * @param
	 */
	public void Hover(String xpath){
		try {
			Actions cat=new Actions(driver);
			cat.moveToElement(driver.findElement(By.xpath(xpath))).perform();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}




}
