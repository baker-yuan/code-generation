package cn.baker.tool.service.impl;

import cn.baker.tool.entity.GenConfig;
import cn.baker.tool.mapper.GenConfigMapper;
import cn.baker.tool.service.GenConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;


/**
 * @author baker.yuan
 */
@Service
@RequiredArgsConstructor
public class GenConfigServiceImpl implements GenConfigService {

    @Resource
    private GenConfigMapper genConfigMapper;

    @Override
    public GenConfig find(String tableName) {
        return genConfigMapper.findByTableName(tableName);
    }

    @Override
    public GenConfig update(String tableName, GenConfig genConfig) {
        String separator = File.separator;
        String[] paths;
        String symbol = "\\";
        if (symbol.equals(separator)) {
            paths = genConfig.getPath().split("\\\\");
        } else {
            paths = genConfig.getPath().split(File.separator);
        }
        StringBuilder api = new StringBuilder();
        for (String path : paths) {
            api.append(path);
            api.append(separator);
            if ("src".equals(path)) {
                api.append("api");
                break;
            }
        }
        genConfig.setApiPath(api.toString());
        genConfigMapper.insertSelective(genConfig);
        return genConfigMapper.selectByPrimaryKey(genConfig.getId());
    }
}
