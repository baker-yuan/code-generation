package cn.baker.tool;

import cn.hutool.extra.template.Template;
import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.TemplateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CodeApplicationTests {

	@Test
	public void contextLoads() {
		TemplateEngine engine = TemplateUtil.createEngine(new TemplateConfig("resources", TemplateConfig.ResourceMode.CLASSPATH));
		Template template = engine.getTemplate("Dto.ftl");
	}

}
