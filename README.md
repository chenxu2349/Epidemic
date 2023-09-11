# lab a1002
epidemic
<br>
ddl: 2023-10

统计区域患者和接触者
http://localhost:8080/countPatientAndPotential?batch=2

查找患者
http://localhost:8080/getPatients?date=2023-07-28&areaCode=10001

查找某个患者的接触者
http://localhost:8080/findContacts?patient_id=10001&batch=1

推理操作
http://localhost:8080/infer?patient_id=10001&batch=1

关联分析
http://localhost:8080/relevance?date=2023-07-29&areaCode=10001

重点对象筛查
http://localhost:8080/keyPersonFilter?date=2023-07-30&batch=3&areaCode=10001

查找某个患者的潜在患者
http://localhost:8080/getPotentialPatients?patient_id=10001&batch=1
