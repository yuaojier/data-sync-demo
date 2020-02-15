package com.kedacom.core.dao;

import entity.CaseInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 案件信息(CaseInfo)表数据库访问层
 *
 * @author makejava
 * @since 2020-02-15 15:09:37
 */
@Mapper
public interface CaseInfoDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    CaseInfo queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<CaseInfo> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param caseInfo 实例对象
     * @return 对象列表
     */
    List<CaseInfo> queryAll(CaseInfo caseInfo);

    /**
     * 新增数据
     *
     * @param caseInfo 实例对象
     * @return 影响行数
     */
    int insert(CaseInfo caseInfo);

    /**
     * 修改数据
     *
     * @param caseInfo 实例对象
     * @return 影响行数
     */
    int update(CaseInfo caseInfo);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}