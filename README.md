# lab a1002
epidemic
<br>
ddl: 2023-10

统计区域患者和接触者
http://localhost:8080/countPatientAndPotential

查找某一天患者
http://localhost:8080/getPatientsByDate?date=2023-07-28

查找某个患者的接触者
http://localhost:8080/findContacts?patient_id=10001&batch=1

推理操作
http://localhost:8080/infer?patient_id=10001&batch=1

关联分析
http://localhost:8080/relevance?date=2023-07-28

重点对象筛查
http://localhost:8080/keyPersonFilter?date=2023-07-28&batch=1

查找某个患者的潜在患者
http://localhost:8080/getPotentialPatients?patient_id=10001&batch=1
