# lab a1002
epidemic
<br>
ddl: 2023-10 (项目延期...)
步骤：1.datapreparetest提取relation0患者接触者映射关系
    2.填充正确的身份证
    3.推理全部
    4.关联全部

统计<--某城市-->全部区域患者和接触者
http://localhost:8080/countPatientAndPotential?date=2023-07-28&cityCode=410100

查找患者（某天某区域）
http://localhost:8080/getPatientsByDateAndAreaCode?date=2023-07-28&areaCode=410102

查找某城市全部患者（某天<--某城市-->全部区域）
http://localhost:8080/getPatientsByDateAndCityCode?date=2023-07-28&cityCode=410100

查找某个患者的接触者
http://localhost:8080/findContacts?patient_id=100001&date=2023-07-28

推理操作
http://localhost:8080/infer?patient_id=100001&date=2023-07-28

关联分析（直接推算所有天和地区的传播链并存入数据库）<--待优化-->
http://localhost:8080/relevanceAll

按天和地区查询传播链
http://localhost:8080/getRelevanceChain?date=2023-07-28&areaCode=410102

重点对象筛查
http://localhost:8080/keyPersonFilter?date=2023-07-28&areaCode=410102

查找某个患者的潜在患者
http://localhost:8080/getPotentialPatients?patient_id=100001&date=2023-07-28

清空接触者概率
http://localhost:8080/clearAllPossibility

插入患者表单
http://localhost:8080/patient

插入接触者表单
http://localhost:8080/contact

趋势预测(区域)
http://localhost:8080/forecastArea?areaCode=410102&date=2023-07-28

趋势预测(城市)
http://localhost:8080/forecastCity?cityCode=410100&date=2023-07-28

推理全部接触者概率
http://localhost:8080/inferAll

清空全部接触者概率
http://localhost:8080/clearAllPossibility