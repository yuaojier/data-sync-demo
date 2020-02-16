package com.kedacom.extractor.response;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaseInfo {
    //案件ID  案件标识
    private String aj_id;
    //案件编号
    private String ajbh;
    //原始编号 警情编号
    private String jqbh;
    //案件名称
    private String ajmc;
    //案件状态
    private String ajzt;
    //案件类型
    private String ajlx;
    //案件来源
    private String ajly;
    //危害程度
    private String ajwhcd;
    //案发开始时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date afsj_ks;
    //案发结束时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date afsj_js;
    //保密状态
    private String classify_status;
    //案件标签
    private String case_tags;
    //案发地点
    private String afddxxdz;
    //经度
    private String map_x;
    //纬度
    private String map_y;
    //所属辖区code
    private String gsdw;
    //所属辖区value
    private String gsdwmc;
    //行政区名称
    private String afddxzqhmc;
    //归类
    private String classification;
    //损失情况
    private String sazjz;
    //报警时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date jjsj;
    //报警人员
    private String barxm;
    //报警单位
    private String bjrgzdw;
    //报警电话
    private String bardh;
    //报警途径
    private String bafs;
    //报警事由
    private String jqlb;
    //报警人陈述
    private String bjms;
    //接报人员
    private String jjry;

    //接报单位
    private String jjdw;
    //受理人员
    private String ajslr;
    //受理单位
    private String ajsldw;
    //受理时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date slsj;
    //案件描述
    private String jyaq;
    //作案时机
    private String xzsj_jh;
    //选择场所
    private String xzcs;
    //伤害部位
    private String injury_part;

    //作案人数
    private String zhzacy;
    //选择对象
    private String xzdx;
    //手段特点
    private String crime_feature;
    //上传时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date cjsj;
    //上传用户
    private String cjyh;
    //上传人员
    private String cjr;
    //更新时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date zxgxsj;
    //更新用户
    private String zxgxyh;
    //更新人员
    private String zxgxr;
    //现场勘验号
    private String xckyh;
    //案件属性
    private String case_attributes;
    //案件性质
    private String ajxz;
    //立案日期
    private Date lasj;
    //破案日期
    private Date pasj;
    //破案单位
    private String padw;
    //破案单位名称
    private String solve_unit_name;

    //案件录入用户
    private String lryh;
    //案件录入用户所属单位
    private String lrdw;
    //是否已串并
    private String is_serial;
    //是否有现场
    private String is_scene;
    //是否勘察现场
    private String sfxcky;
}
