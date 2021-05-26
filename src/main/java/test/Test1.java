package test;

//import antlr.HplsqlLexer;
//import antlr.HplsqlParser;
//import org.antlr.v4.runtime.ANTLRInputStream;
//import org.antlr.v4.runtime.CommonTokenStream;
//import org.antlr.v4.runtime.tree.ParseTree;


import com.owen.hivesql.entity.QueryResult;
import com.owen.hivesql.exception.parseRunningException;
import com.owen.hivesql.handletree.SemanticAnalyzer;
import com.owen.hivesql.util.ParseSqlUtil;
import com.owen.hivesql.util.TokProcessor;
import com.owen.hivesql.hivecode.ASTNode;
import com.owen.hivesql.hivecode.ParseException;

import java.io.IOException;

/**
 * @ProjectName: Sql
 * @Package: test
 * @ClassName: Test1
 * @Author: owen
 * @Description:
 * @Date: 2021/3/29 18:40
 * @Version: 1.0
 */
public class Test1 {

    static String sql="select \n" +
            "\tid\n" +
            "\t,name\n" +
            "\t,company \n" +
            "\n" +
            "from \n" +
            "(\n" +
            "\tselect \n" +
            "\t\tid\n" +
            "\t\t,name \n" +
            "\tfrom \n" +
            "\ttable1\n" +
            "\t\n" +
            ")t1\n" +
            "join \n" +
            "\n" +
            "(\n" +
            "\tselect \n" +
            "\t\tid\n" +
            "\t\t,company \n" +
            "\tfrom \n" +
            "\ttable2\n" +
            "\t\n" +
            ")t2\n" +
            "\n" +
            "on t1.id = t2.id" ;

    static StringBuffer sb = new StringBuffer("insert overwrite table olap.olap_ceof_control_cabin_middle_core_stat_v1_tt3_di partition (pt='20210511000000')\n" +
            "\n" +
            "select stat_date \n" +
            "       ,stat_mon \n" +
            "       ,stat_year \n" +
            "       ,brand_name \n" +
            "       ,business_name \n" +
            "       ,city_code \n" +
            "       ,city_name \n" +
            "       ,city_category \n" +
            "       ,sum(dau_day)\n" +
            "       ,sum(dau_ke_app_day)\n" +
            "       ,battle_region_code\n" +
            "       ,battle_region_abbr_name\n" +
            "       ,province_region_code\n" +
            "       ,province_region_abbr_name\n" +
            "       ,sum(dau_lianjia_app_day)\n" +
            "       ,sum(dau_mon)\n" +
            "       ,sum(dau_year)\n" +
            "       ,sum(dau_ke_app_mon)\n" +
            "       ,sum(dau_ke_app_year)\n" +
            "       ,sum(dau_lianjia_app_mon)\n" +
            "       ,sum(dau_lianjia_app_year)\n" +
            "       ,performance_city_code -- 业绩城市编码\n" +
            "       ,performance_city_abbr -- 业绩城市简称\n" +
            "       ,null as dp\n" +
            "       ,business_region_abbr_name\n" +
            "       ,business_region_abbr_code\n" +
            "     from  (\n" +
            "      select stat_date \n" +
            "       ,stat_mon \n" +
            "       ,stat_year \n" +
            "       ,case when brand_name is not null then brand_name else '全部' end as brand_name\n" +
            "       ,city_category \n" +
            "       ,case when city_code is not null then city_code  else '-1' end as city_code\n" +
            "       ,case when city_name is not null then city_name else  '全部' end as city_name\n" +
            "       ,case when business_name is not null then business_name else '全部' end as business_name\n" +
            "       ,dau_day\n" +
            "       ,dau_ke_app_day\n" +
            "       ,case when battle_region_code is not null then battle_region_code else '-1' end battle_region_code\n" +
            "       ,case when battle_region_abbr_name is not null then battle_region_abbr_name else '全部' end battle_region_abbr_name\n" +
            "       ,case when province_region_code is not null then province_region_code else '-1' end province_region_code\n" +
            "       ,case when province_region_abbr_name is not null then province_region_abbr_name else '全部' end province_region_abbr_name\n" +
            "       ,dau_lianjia_app_day\n" +
            "       ,dau_mon\n" +
            "       ,dau_year\n" +
            "       ,dau_ke_app_mon\n" +
            "       ,dau_ke_app_year\n" +
            "       ,dau_lianjia_app_mon\n" +
            "       ,dau_lianjia_app_year\n" +
            "       ,case when performance_city_code is not null then performance_city_code else '-1' end as performance_city_code\n" +
            "       ,case when performance_city_abbr is not null then performance_city_abbr else '全部' end as performance_city_abbr\n" +
            "       ,case when business_region_abbr_name is not null then business_region_abbr_name else '全国' end as business_region_abbr_name\n" +
            "       ,case when business_region_abbr_code is not null then business_region_abbr_code else '-1' end as business_region_abbr_code\n" +
            "     from (\n" +
            "       SELECT\n" +
            "\n" +
            "        stat_date \n" +
            "       ,stat_mon \n" +
            "       ,stat_year \n" +
            "       ,brand_name \n" +
            "       ,city_category \n" +
            "       ,city_code \n" +
            "       ,city_name \n" +
            "       ,business_name \n" +
            "       ,battle_region_code \n" +
            "       ,battle_region_abbr_name \n" +
            "       ,province_region_code \n" +
            "       ,province_region_abbr_name\n" +
            "       ,count(distinct dau) dau_day\n" +
            "       ,count(distinct dau_ke_app) dau_ke_app_day\n" +
            "       ,count(distinct dau_lianjia_app) dau_lianjia_app_day\n" +
            "       ,null dau_mon\n" +
            "       ,null dau_ke_app_mon\n" +
            "       ,null dau_lianjia_app_mon\n" +
            "       ,null dau_year\n" +
            "       ,null dau_ke_app_year\n" +
            "       ,null dau_lianjia_app_year\n" +
            "       ,performance_city_code -- 业绩城市编码\n" +
            "       ,performance_city_abbr -- 业绩城市简称\n" +
            "       ,business_region_abbr_name\n" +
            "       ,business_region_abbr_code\n" +
            "       FROM\n" +
            "       (\n" +
            "      \n" +
            "      SELECT  a.stat_date stat_date \n" +
            "              ,a.stat_mon stat_mon \n" +
            "              ,a.stat_year \n" +
            "              ,a.brand_name \n" +
            "              ,a.city_category \n" +
            "              ,a.city_code \n" +
            "              ,a.city_name \n" +
            "              ,a.business_name \n" +
            "              ,a.battle_region_code -- 战区编码 \n" +
            "              ,a.battle_region_abbr_name -- 战区名 \n" +
            "              ,a.province_region_code -- 省区编码 \n" +
            "              ,a.province_region_abbr_name -- 省区名 \n" +
            "              ,a.dau  \n" +
            "              ,a.dau_ke_app\n" +
            "              ,a.dau_lianjia_app\n" +
            "              ,a.performance_city_code -- 业绩城市编码\n" +
            "              ,a.performance_city_abbr -- 业绩城市简称\n" +
            "              ,case when performance_city_abbr in ('北京','上海') then '北上' else '贝壳coo' end as business_region_abbr_name\n" +
            "              ,case when performance_city_code in ('110000','310000') then '-100' else '-200' end as business_region_abbr_code\n" +
            "       FROM olap.olap_ceof_leadership_cockpit_middle_core_detail_accu_tt3_di a\n" +
            "       where pt='20210511000000'\n" +
            "\n" +
            "       )a \n" +
            "       GROUP BY   stat_date ,stat_mon ,stat_year ,brand_name ,city_category ,city_code ,city_name ,business_name ,business_region_abbr_name\n" +
            "       ,business_region_abbr_code,battle_region_code ,battle_region_abbr_name ,province_region_code ,province_region_abbr_name,performance_city_code,performance_city_abbr\n" +
            "       GROUPING SETS (\n" +
            "              (stat_date,stat_mon,stat_year,brand_name,city_category,city_code,city_name,business_name,business_region_abbr_name\n" +
            "       ,business_region_abbr_code,battle_region_code,battle_region_abbr_name,province_region_code,province_region_abbr_name,performance_city_code,performance_city_abbr), \n" +
            "              (stat_date,stat_mon,stat_year,brand_name,city_category,business_name,business_region_abbr_name\n" +
            "       ,business_region_abbr_code,battle_region_code,battle_region_abbr_name,province_region_code,province_region_abbr_name,performance_city_code,performance_city_abbr),\n" +
            "              (stat_date,stat_mon,stat_year,brand_name,city_category,business_name,business_region_abbr_name\n" +
            "       ,business_region_abbr_code,battle_region_code,battle_region_abbr_name,province_region_code,province_region_abbr_name), \n" +
            "\n" +
            "              (stat_date,stat_mon,stat_year,brand_name,city_category,business_name,business_region_abbr_name\n" +
            "       ,business_region_abbr_code,battle_region_code,battle_region_abbr_name), \n" +
            "               (stat_date,stat_mon,stat_year,brand_name,city_category,business_name,business_region_abbr_name\n" +
            "       ,business_region_abbr_code), \n" +
            "              (stat_date,stat_mon,stat_year,brand_name,city_category,business_name),\n" +
            "              \n" +
            "\n" +
            "\n" +
            "              (stat_date,stat_mon,stat_year,brand_name,city_category,city_code,city_name,business_region_abbr_name\n" +
            "       ,business_region_abbr_code,battle_region_code,battle_region_abbr_name,province_region_code,province_region_abbr_name,performance_city_code,performance_city_abbr), \n" +
            "              (stat_date,stat_mon,stat_year,brand_name,city_category,business_region_abbr_name\n" +
            "       ,business_region_abbr_code,battle_region_code,battle_region_abbr_name,province_region_code,province_region_abbr_name,performance_city_code,performance_city_abbr),\n" +
            "              (stat_date,stat_mon,stat_year,brand_name,city_category,business_region_abbr_name\n" +
            "       ,business_region_abbr_code,battle_region_code,battle_region_abbr_name,province_region_code,province_region_abbr_name), \n" +
            "\n" +
            "              (stat_date,stat_mon,stat_year,brand_name,city_category,business_region_abbr_name\n" +
            "       ,business_region_abbr_code,battle_region_code,battle_region_abbr_name),\n" +
            "              (stat_date,stat_mon,stat_year,brand_name,city_category,business_region_abbr_name\n" +
            "       ,business_region_abbr_code), \n" +
            "              (stat_date,stat_mon,stat_year,brand_name,city_category)\n" +
            "              \n" +
            "\n" +
            "\n" +
            "              \n" +
            "\n" +
            "       ) \n" +
            "       )qq\n" +
            "\n" +
            "       union all \n" +
            "\n" +
            "       select stat_date \n" +
            "       ,stat_mon \n" +
            "       ,stat_year \n" +
            "       ,case when brand_name is not null then brand_name else '全部' end as brand_name\n" +
            "       ,city_category \n" +
            "       ,case when city_code is not null then city_code  else '-1' end as city_code\n" +
            "       ,case when city_name is not null then city_name else  '全部' end as city_name\n" +
            "       ,case when business_name is not null then business_name else  '全部' end as business_name\n" +
            "       ,dau_day\n" +
            "       ,dau_ke_app_day\n" +
            "       ,case when battle_region_code is not null then battle_region_code else '-1' end battle_region_code\n" +
            "       ,case when battle_region_abbr_name is not null then battle_region_abbr_name else '全部' end battle_region_abbr_name\n" +
            "       ,case when province_region_code is not null then province_region_code else '-1' end province_region_code\n" +
            "       ,case when province_region_abbr_name is not null then province_region_abbr_name else '全部' end province_region_abbr_name\n" +
            "       ,dau_lianjia_app_day\n" +
            "       ,dau_mon\n" +
            "       ,dau_year\n" +
            "       ,dau_ke_app_mon\n" +
            "       ,dau_ke_app_year\n" +
            "       ,dau_lianjia_app_mon\n" +
            "       ,dau_lianjia_app_year\n" +
            "       ,case when performance_city_code is not null then  performance_city_code else '-1' end as performance_city_code-- 业绩城市编码\n" +
            "       ,case when performance_city_abbr is not null then  performance_city_abbr else '全部' end as performance_city_abbr-- 业绩城市简称\n" +
            "       ,case when business_region_abbr_name is not null then business_region_abbr_name else '全国' end as business_region_abbr_name\n" +
            "       ,case when business_region_abbr_code is not null then business_region_abbr_code else '-1' end as business_region_abbr_code\n" +
            "     from (\n" +
            "       SELECT\n" +
            "\n" +
            "        stat_date \n" +
            "       ,stat_mon \n" +
            "       ,stat_year \n" +
            "       ,brand_name \n" +
            "       ,city_category \n" +
            "       ,city_code \n" +
            "       ,city_name \n" +
            "       ,business_name \n" +
            "       ,battle_region_code \n" +
            "       ,battle_region_abbr_name \n" +
            "       ,province_region_code \n" +
            "       ,province_region_abbr_name\n" +
            "       ,null dau_day\n" +
            "       ,null dau_ke_app_day\n" +
            "       ,null dau_lianjia_app_day\n" +
            "       ,count(distinct dau) dau_mon\n" +
            "       ,count(distinct dau_ke_app) dau_ke_app_mon\n" +
            "       ,count(distinct dau_lianjia_app) dau_lianjia_app_mon\n" +
            "       ,null dau_year\n" +
            "       ,null dau_ke_app_year\n" +
            "       ,null dau_lianjia_app_year\n" +
            "       ,performance_city_code -- 业绩城市编码\n" +
            "       ,performance_city_abbr -- 业绩城市简称\n" +
            "       ,business_region_abbr_name\n" +
            "       ,business_region_abbr_code\n" +
            "       FROM\n" +
            "       (\n" +
            "      \n" +
            "      SELECT  a.stat_date stat_date \n" +
            "              ,a.stat_mon stat_mon \n" +
            "              ,a.stat_year \n" +
            "              ,a.brand_name \n" +
            "              ,a.city_category \n" +
            "              ,a.city_code \n" +
            "              ,a.city_name \n" +
            "              ,a.business_name \n" +
            "              ,a.battle_region_code -- 战区编码 \n" +
            "              ,a.battle_region_abbr_name -- 战区名 \n" +
            "              ,a.province_region_code -- 省区编码 \n" +
            "              ,a.province_region_abbr_name -- 省区名 \n" +
            "              ,a.dau \n" +
            "              ,a.dau_ke_app\n" +
            "              ,a.dau_lianjia_app\n" +
            "              ,a.performance_city_code -- 业绩城市编码\n" +
            "              ,a.performance_city_abbr -- 业绩城市简称\n" +
            "              ,case when performance_city_abbr in ('北京','上海') then '北上' else '贝壳coo' end as business_region_abbr_name\n" +
            "              ,case when performance_city_code in ('110000','310000') then '-100' else '-200' end as business_region_abbr_code\n" +
            "       FROM  olap.olap_ceof_leadership_cockpit_middle_core_detail_accu_tt3_mi a\n" +
            "       where pt='20210511000000'\n" +
            "       \n" +
            "\n" +
            "       )a \n" +
            "       GROUP BY   stat_date ,stat_mon ,stat_year ,brand_name ,city_category ,city_code ,city_name ,business_name,business_region_abbr_name\n" +
            "       ,business_region_abbr_code,battle_region_code ,battle_region_abbr_name ,province_region_code ,province_region_abbr_name,performance_city_code,performance_city_abbr\n" +
            "       GROUPING SETS (\n" +
            "              (stat_date,stat_mon,stat_year,brand_name,city_category,city_code,city_name,business_name,business_region_abbr_name\n" +
            "       ,business_region_abbr_code,battle_region_code,battle_region_abbr_name,province_region_code,province_region_abbr_name,performance_city_code,performance_city_abbr), \n" +
            "              (stat_date,stat_mon,stat_year,brand_name,city_category,business_name,business_region_abbr_name\n" +
            "       ,business_region_abbr_code,battle_region_code,battle_region_abbr_name,province_region_code,province_region_abbr_name,performance_city_code,performance_city_abbr), \n" +
            "              (stat_date,stat_mon,stat_year,brand_name,city_category,business_name,business_region_abbr_name\n" +
            "       ,business_region_abbr_code,battle_region_code,battle_region_abbr_name,province_region_code,province_region_abbr_name),\n" +
            "              (stat_date,stat_mon,stat_year,brand_name,city_category,business_name,business_region_abbr_name\n" +
            "       ,business_region_abbr_code,battle_region_code,battle_region_abbr_name), \n" +
            "              (stat_date,stat_mon,stat_year,brand_name,city_category,business_name,business_region_abbr_name\n" +
            "       ,business_region_abbr_code), \n" +
            "              (stat_date,stat_mon,stat_year,brand_name,city_category,business_name),\n" +
            "            \n" +
            "\n" +
            "\n" +
            "\n" +
            "               (stat_date,stat_mon,stat_year,brand_name,city_category,city_code,city_name,business_region_abbr_name\n" +
            "       ,business_region_abbr_code,battle_region_code,battle_region_abbr_name,province_region_code,province_region_abbr_name,performance_city_code,performance_city_abbr), \n" +
            "              (stat_date,stat_mon,stat_year,brand_name,city_category,business_region_abbr_name\n" +
            "       ,business_region_abbr_code,battle_region_code,battle_region_abbr_name,province_region_code,province_region_abbr_name,performance_city_code,performance_city_abbr), \n" +
            "              (stat_date,stat_mon,stat_year,brand_name,city_category,business_region_abbr_name\n" +
            "       ,business_region_abbr_code,battle_region_code,battle_region_abbr_name,province_region_code,province_region_abbr_name),\n" +
            "              (stat_date,stat_mon,stat_year,brand_name,city_category,business_region_abbr_name\n" +
            "       ,business_region_abbr_code,battle_region_code,battle_region_abbr_name), \n" +
            "              (stat_date,stat_mon,stat_year,brand_name,city_category,business_region_abbr_name\n" +
            "       ,business_region_abbr_code),\n" +
            "              (stat_date,stat_mon,stat_year,brand_name,city_category)\n" +
            "               \n" +
            "              \n" +
            "       ) \n" +
            "       )qq\n" +
            "       union all \n" +
            "\n" +
            "       select  stat_date \n" +
            "       ,stat_mon \n" +
            "       ,stat_year \n" +
            "       ,case when brand_name is not null then brand_name else '全部' end as brand_name\n" +
            "       ,city_category \n" +
            "       ,case when city_code is not null then city_code  else '-1' end as city_code\n" +
            "       ,case when city_name is not null then city_name else  '全部' end as city_name\n" +
            "       ,case when business_name is not null then business_name else '全部' end as business_name\n" +
            "       ,dau_day\n" +
            "       ,dau_ke_app_day\n" +
            "       ,case when battle_region_code is not null then battle_region_code else '-1' end battle_region_code\n" +
            "       ,case when battle_region_abbr_name is not null then battle_region_abbr_name else '全部' end battle_region_abbr_name\n" +
            "       ,case when province_region_code is not null then province_region_code else '-1' end province_region_code\n" +
            "       ,case when province_region_abbr_name is not null then province_region_abbr_name else '全部' end province_region_abbr_name\n" +
            "       ,dau_lianjia_app_day\n" +
            "       ,dau_mon\n" +
            "       ,dau_year\n" +
            "       ,dau_ke_app_mon\n" +
            "       ,dau_ke_app_year\n" +
            "       ,dau_lianjia_app_mon\n" +
            "       ,dau_lianjia_app_year\n" +
            "       ,case when performance_city_code is not null then performance_city_code else '-1' end as performance_city_code -- 业绩城市编码\n" +
            "       ,case when performance_city_abbr is not null then performance_city_abbr else '全部' end as performance_city_abbr -- 业绩城市简称\n" +
            "       ,case when business_region_abbr_name is not null then business_region_abbr_name else '全国' end as business_region_abbr_name\n" +
            "       ,case when business_region_abbr_code is not null then business_region_abbr_code else '-1' end as business_region_abbr_code\n" +
            "     from (\n" +
            "       SELECT\n" +
            "\n" +
            "        stat_date \n" +
            "       ,stat_mon \n" +
            "       ,stat_year \n" +
            "       ,brand_name \n" +
            "       ,city_category \n" +
            "       ,city_code \n" +
            "       ,city_name \n" +
            "       ,business_name \n" +
            "       ,battle_region_code \n" +
            "       ,battle_region_abbr_name \n" +
            "       ,province_region_code \n" +
            "       ,province_region_abbr_name\n" +
            "       ,null dau_day\n" +
            "       ,null  dau_ke_app_day\n" +
            "       ,null  dau_lianjia_app_day\n" +
            "       ,null dau_mon\n" +
            "       ,null dau_ke_app_mon\n" +
            "       ,null dau_lianjia_app_mon\n" +
            "       ,count(distinct dau) dau_year\n" +
            "       ,count(distinct dau_ke_app) dau_ke_app_year\n" +
            "       ,count(distinct dau_lianjia_app) dau_lianjia_app_year\n" +
            "       ,performance_city_code -- 业绩城市编码\n" +
            "       ,performance_city_abbr -- 业绩城市简称\n" +
            "       ,business_region_abbr_name\n" +
            "       ,business_region_abbr_code\n" +
            "       FROM\n" +
            "       (\n" +
            "      \n" +
            "      SELECT  a.stat_date stat_date \n" +
            "              ,a.stat_mon stat_mon \n" +
            "              ,a.stat_year \n" +
            "              ,a.brand_name \n" +
            "              ,a.city_category \n" +
            "              ,a.city_code \n" +
            "              ,a.city_name \n" +
            "              ,a.business_name \n" +
            "              ,a.battle_region_code -- 战区编码 \n" +
            "              ,a.battle_region_abbr_name -- 战区名 \n" +
            "              ,a.province_region_code -- 省区编码 \n" +
            "              ,a.province_region_abbr_name -- 省区名 \n" +
            "              ,a.dau  \n" +
            "              ,a.dau_ke_app\n" +
            "              ,a.dau_lianjia_app\n" +
            "              ,a.performance_city_code -- 业绩城市编码\n" +
            "              ,a.performance_city_abbr -- 业绩城市简称\n" +
            "              ,case when performance_city_abbr in ('北京','上海') then '北上' else '贝壳coo' end as business_region_abbr_name\n" +
            "              ,case when performance_city_code in ('110000','310000') then '-100' else '-200' end as business_region_abbr_code\n" +
            "       FROM  olap.olap_ceof_leadership_cockpit_middle_core_detail_accu_tt3_yi a\n" +
            "       where pt='20210511000000'\n" +
            "       \n" +
            "\n" +
            "       )a \n" +
            "       GROUP BY   stat_date ,stat_mon ,stat_year ,brand_name ,city_category ,city_code ,city_name ,business_name ,business_region_abbr_name\n" +
            "       ,business_region_abbr_code,battle_region_code ,battle_region_abbr_name ,province_region_code ,province_region_abbr_name,performance_city_code,performance_city_abbr\n" +
            "       GROUPING SETS (\n" +
            "              (stat_date,stat_mon,stat_year,brand_name,city_category,city_code,city_name,business_name,business_region_abbr_name\n" +
            "       ,business_region_abbr_code,battle_region_code,battle_region_abbr_name,province_region_code,province_region_abbr_name,performance_city_code,performance_city_abbr), \n" +
            "              (stat_date,stat_mon,stat_year,brand_name,city_category,business_name,business_region_abbr_name\n" +
            "       ,business_region_abbr_code,battle_region_code,battle_region_abbr_name,province_region_code,province_region_abbr_name,performance_city_code,performance_city_abbr),\n" +
            "               (stat_date,stat_mon,stat_year,brand_name,city_category,business_name,business_region_abbr_name\n" +
            "       ,business_region_abbr_code,battle_region_code,battle_region_abbr_name,province_region_code,province_region_abbr_name),\n" +
            "              (stat_date,stat_mon,stat_year,brand_name,city_category,business_name,business_region_abbr_name\n" +
            "       ,business_region_abbr_code,battle_region_code,battle_region_abbr_name), \n" +
            "       (stat_date,stat_mon,stat_year,brand_name,city_category,business_name,business_region_abbr_name\n" +
            "       ,business_region_abbr_code), \n" +
            "              (stat_date,stat_mon,stat_year,brand_name,city_category,business_name),\n" +
            "              \n" +
            "\n" +
            "\n" +
            "\n" +
            "              (stat_date,stat_mon,stat_year,brand_name,city_category,city_code,city_name,business_region_abbr_name\n" +
            "       ,business_region_abbr_code,battle_region_code,battle_region_abbr_name,province_region_code,province_region_abbr_name,performance_city_code,performance_city_abbr), \n" +
            "              (stat_date,stat_mon,stat_year,brand_name,city_category,business_region_abbr_name\n" +
            "       ,business_region_abbr_code,battle_region_code,battle_region_abbr_name,province_region_code,province_region_abbr_name,performance_city_code,performance_city_abbr),\n" +
            "               (stat_date,stat_mon,stat_year,brand_name,city_category,business_region_abbr_name\n" +
            "       ,business_region_abbr_code,battle_region_code,battle_region_abbr_name,province_region_code,province_region_abbr_name),\n" +
            "              (stat_date,stat_mon,stat_year,brand_name,city_category,business_region_abbr_name\n" +
            "       ,business_region_abbr_code,battle_region_code,battle_region_abbr_name),\n" +
            "              (stat_date,stat_mon,stat_year,brand_name,city_category,business_region_abbr_name\n" +
            "       ,business_region_abbr_code),  \n" +
            "              (stat_date,stat_mon,stat_year,brand_name,city_category)\n" +
            "             \n" +
            "\n" +
            "              \n" +
            "       ) \n" +
            "       )qq\n" +
            " )t\n" +
            "group by stat_date \n" +
            "       ,stat_mon \n" +
            "       ,stat_year \n" +
            "       ,brand_name \n" +
            "       ,city_category \n" +
            "       ,city_code \n" +
            "       ,city_name \n" +
            "       ,business_name \n" +
            "       ,battle_region_code\n" +
            "       ,battle_region_abbr_name\n" +
            "       ,province_region_code\n" +
            "       ,province_region_abbr_name\n" +
            "       ,performance_city_code\n" +
            "       ,performance_city_abbr\n" +
            "       ,business_region_abbr_name\n" +
            "       ,business_region_abbr_code\n"
            );
//            "select a from b where a in (select a from (select a from b)tt  left join (select a from dw.b) t2)";
//            "select a from (select a from b)tt  left join (select a from dw.b) t2" ;
//            "select a from (select a from b)t4 left join \n" +
//                    "\n" +
//                    "( select q from (select c from d)t left join (select e from f)t2 )t3 left join (select a from b)t8 " ;
 //  "select a from (select a from b)t left join (select c from d)t1 left join (select e from f)t2 " ;
//            "select \n" +
//            "\tt1.a\n" +
//            "\t,count(b) as b\n" +
//            "\t,count(c) as c\n" +
//            "from(\n" +
//            "\tselect if(a=1,q,z),b  from dw.dw_dec_aquaman_quotation_info_da  where c = 1 and d in (select d from t3 where e=1) limit 0,10\n" +
//            ")t1\n" +
//            "left join \n" +
//            "(\n" +
//            "\tselect f(a=1,q,z) a ,c  from \n" +
//            "\t(select a,c from dw.dw_dec_aquaman_quotation_info_da  where c = 1 and d=1  ) tt2 \n" +
//            "\twhere a = 1 and c = 2\n" +
//            ")t2\n" +
//            "on t1.a = t2.a  \n" +
//            "where b = 7\n" +
//            "group by a\n";

    public static void main(String[] args) throws IOException {
//        InputStream input1 = new ByteArrayInputStream(sql.getBytes("UTF-8"));
//
//        ANTLRInputStream input = new ANTLRInputStream(input1);  //将输入转成antlr的input流
//
//        HplsqlLexer lexer = new HplsqlLexer(new ANTLRInputStream(input1));  //词法分析
//
//        CommonTokenStream tokens = new CommonTokenStream(lexer);  //转成token流
//        HplsqlParser parser = new HplsqlParser(tokens); // 语法分析
//        HplsqlParser.StmtContext stmt = parser.stmt();
//
//        System.out.println(stmt.toStringTree(parser)); //打印规则数
//        InputStream input = new ByteArrayInputStream(sql.getBytes("UTF-8"));
//        HplsqlLexer lexer = new HplsqlLexer(new ANTLRInputStream(input));
//        CommonTokenStream tokens = new CommonTokenStream(lexer);
//        HplsqlParser parser = new HplsqlParser(tokens);
//        HplsqlParser.StmtContext stmt = parser.stmt();
//
//
//        System.out.println(stmt.toStringTree());

        QueryResult queryResult = null;
        try {
           // ASTNode tree = ParseUtils.parse(sql, null);
            ASTNode tree = ParseSqlUtil.getAstTree(sb.toString());
            System.out.println(tree.toStringTree());
            SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer();

            queryResult = semanticAnalyzer.handlerHeadTree(tree);

            System.out.println(queryResult.getTableAlias().size());

//            ASTNode child = (ASTNode)tree.getChild(0).getChild(0).getChild(0).getChild(0).getChild(1).getChild(1).getChild(0).getChild(0);
//            System.out.println(child.toStringTree());
//            System.out.println(child.getToken().getText());

        } catch (ParseException e) {
            System.out.println( e.getMessage());
            e.printStackTrace();
        }catch (parseRunningException e) {
            e.printStackTrace();
        }


    }
}
