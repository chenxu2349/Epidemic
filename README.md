# lab a1002
epidemic
<br>
ddl: 2023-10

统计区域患者和接触者
http://localhost:8080/countPatientAndPotential?batch=2

查找患者（某天某区域）
http://localhost:8080/getPatients?date=2023-07-28&areaCode=10001

查找患者（某天全部区域）
http://localhost:8080/getPatientsByDate?date=2023-07-28

查找某个患者的接触者
http://localhost:8080/findContacts?patient_id=10001&batch=1

推理操作
http://localhost:8080/infer?patient_id=10001&batch=1

关联分析（直接推算所有天和地区的传播链并存入数据库）
http://localhost:8080/relevanceAll

按天和地区查询传播链
http://localhost:8080/getRelevanceChain?batch=1&areaCode=10001

重点对象筛查
http://localhost:8080/keyPersonFilter?date=2023-07-30&batch=3&areaCode=10001

查找某个患者的潜在患者
http://localhost:8080/getPotentialPatients?patient_id=10001&batch=1
