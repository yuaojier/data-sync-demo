package com.kedacom.core;

import com.kedacom.core.api.CaseInfoService;
import com.kedacom.core.dao.CaseInfoDao;
import entity.CaseInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 案件信息(CaseInfo)表服务实现类
 *
 * @author makejava
 * @since 2020-02-15 15:09:40
 */
@Service
public class CaseInfoServiceImpl implements CaseInfoService {
    @Resource
    private CaseInfoDao caseInfoDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public CaseInfo queryById(Long id) {
        return this.caseInfoDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<CaseInfo> queryAllByLimit(int offset, int limit) {
        return this.caseInfoDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param caseInfo 实例对象
     * @return 实例对象
     */
    @Override
    public CaseInfo insert(CaseInfo caseInfo) {
        this.caseInfoDao.insert(caseInfo);
        return caseInfo;
    }

    /**
     * 修改数据
     *
     * @param caseInfo 实例对象
     * @return 实例对象
     */
    @Override
    public CaseInfo update(CaseInfo caseInfo) {
        this.caseInfoDao.update(caseInfo);
        return this.queryById(caseInfo.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.caseInfoDao.deleteById(id) > 0;
    }
}