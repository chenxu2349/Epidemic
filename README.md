# lab a1002
epidemic
<br>
ddl: 2023-10 (项目延期...)

统计区域患者和接触者
http://localhost:8080/countPatientAndPotential?date=2023-07-28

查找患者（某天某区域）
http://localhost:8080/getPatientsByDateAndAreaCode?date=2023-07-28&areaCode=101010

查找患者（某天全部区域）
http://localhost:8080/getPatientsByDate?date=2023-07-28

查找某个患者的接触者
http://localhost:8080/findContacts?patient_id=100001&date=2023-07-28

推理操作
http://localhost:8080/infer?patient_id=100001&date=2023-07-28

关联分析（直接推算所有天和地区的传播链并存入数据库）
http://localhost:8080/relevanceAll

按天和地区查询传播链
http://localhost:8080/getRelevanceChain?date=2023-07-28&areaCode=101010

重点对象筛查
http://localhost:8080/keyPersonFilter?date=2023-07-28&areaCode=101010

查找某个患者的潜在患者
http://localhost:8080/getPotentialPatients?patient_id=100001&date=2023-07-28

清空接触者概率
http://localhost:8080/clearAllPossibility

插入患者表单
http://localhost:8080/patient

插入接触者表单
http://localhost:8080/contact

趋势预测(如果全市，就把10002替换成all)
http://localhost:8080/forecast?areaCode=101010&date=2023-07-28

推理全部接触者概率
http://localhost:8080/inferAll

清空全部接触者概率
http://localhost:8080/clearAllPossibility