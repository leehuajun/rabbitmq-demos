package cn.cvtb.demo03scriptengine;

import apple.applescript.AppleScriptEngine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * JDK自带的类可以实现调用js的功能，可以实现执行字符串中的运算公式的功能。
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Demo03ScriptEngineApplicationTests {

	private ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");

	/**
	 * 注意：如果公式中存在变量作为运算的元素的话，可以借鉴使用replaceAll()方法将相应的变量替换成实际的数值。如果是多个变量可以利用循环遍历的方式来解决。
	 * 例子：jse.eval("1+b".replaceAll("b", b.toString()))；
	 */
	@Test
	public void contextLoads() {
		java.text.DecimalFormat df = new java.text.DecimalFormat("#,###.##");
		String strs = "1+1*2+(10-5/3)";
//		String strs = "1+1*2+(10-5/2)*100%";  // 不认识100%，出错
		try {
			Double value = (Double) jse.eval(strs);
			String strValue = df.format(value);
			System.out.println(strValue);
		} catch (ScriptException e) {
			e.printStackTrace();
		}

	}

}
