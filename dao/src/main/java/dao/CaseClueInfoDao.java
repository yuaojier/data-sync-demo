package dao;

import entity.CaseClueInfo;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (CaseClueInfo)表数据库访问层
 *
 * @author makejava
 * @since 2020-02-11 11:10:37
 */
public interface CaseClueInfoDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    CaseClueInfo queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<CaseClueInfo> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param caseClueInfo 实例对象
     * @return 对象列表
     */
    List<CaseClueInfo> queryAll(CaseClueInfo caseClueInfo);

    /**
     * 新增数据
     *
     * @param caseClueInfo 实例对象
     * @return 影响行数
     */
    int insert(CaseClueInfo caseClueInfo);

    /**
     * 修改数据
     *
     * @param caseClueInfo 实例对象
     * @return 影响行数
     */
    int update(CaseClueInfo caseClueInfo);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}