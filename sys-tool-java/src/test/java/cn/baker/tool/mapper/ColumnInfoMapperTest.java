package cn.baker.tool.mapper;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ColumnInfoMapperTest {

    @Resource
    private ColumnInfoMapper columnInfoMapper;

//    @Test
//    public void getTables() {
//        List<TableInfoVO> tableInfoVoList = columnInfoMapper.getTables();
//        tableInfoVoList.forEach(System.out::println);
//    }
}