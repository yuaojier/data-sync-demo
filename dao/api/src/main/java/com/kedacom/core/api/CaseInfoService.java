package com.kedacom.core.api;



import entity.CaseInfo;

import java.util.List;

/**
 * 案件信息(CaseInfo)表服务接口
 *
 * @author makejava
 * @since 2020-02-15 15:09:37
 */
public interface CaseInfoService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    CaseInfo queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<CaseInfo> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param caseInfo 实例对象
     * @return 实例对象
     */
    CaseInfo insert(CaseInfo caseInfo);

    /**
     * 修改数据
     *
     * @param caseInfo 实例对象
     * @return 实例对象
     */
    CaseInfo update(CaseInfo caseInfo);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}