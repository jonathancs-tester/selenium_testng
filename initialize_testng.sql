--DELETE REPORTS

DELETE FROM reportcontractinformation;
DELETE FROM reportevolution;
DELETE FROM reportgenerationrequest;
DELETE FROM reporthardware;
DELETE FROM reportlevelling;
DELETE FROM reportlevellingconsolidated;
DELETE FROM reportlifecycle;
DELETE FROM reportlocation;
DELETE FROM reportsalespricing;
DELETE FROM reportsearch;
DELETE FROM reportsoftversion;
DELETE FROM reportstatisticaccess;
DELETE FROM reportstatisticreport;
DELETE FROM queue_task;
DELETE FROM report;

-- DELETE SCHEDULES

DELETE FROM logschedulearnecollect;
DELETE FROM logschedulemobatchnodecollect;
DELETE FROM logschedulenodecollect;
DELETE FROM logautomaticparser;
DELETE FROM log;
DELETE FROM auditedossnodes;
DELETE FROM auditednodes;
DELETE FROM auditednewnodes;
DELETE FROM auditeddamanodes;
DELETE FROM audit;
DELETE FROM scheduledproducttypenodese_nodes;
DELETE FROM schedulearnecollect_oss;
DELETE FROM scheduleproducttypenodes;
DELETE FROM weekdaysschedulearnecollect;
DELETE FROM weekdaysschedulenodecollect;
DELETE FROM weekdaysschedulenodemobatchcollect;
DELETE FROM schedulenodecollect;
DELETE FROM schedulearnecollect;
DELETE FROM schedulemobatchnodecollect;

-- DELETE CUSTOMERCOUNTRY

DELETE 
FROM customercountry
WHERE idcustomer IN (SELECT idcustomer 
					 FROM customer
					 WHERE customername='EditCustomer'); 

DELETE 
FROM customercountry
WHERE idcountry IN (SELECT idcountry 
					 FROM country
					 WHERE countryname='EditCountry'); 
 
DELETE FROM account
WHERE nameaccount='EditAccount';
 
DELETE FROM country
WHERE countryname='EditCountry';
 
DELETE FROM customer
WHERE customername='EditCustomer';

DELETE FROM customerunit
WHERE namecustomerunit='EditUnit';

DELETE 
FROM customercountry
WHERE idcustomer IN (SELECT idcustomer 
					 FROM customer
					 WHERE customername='AddCustomer'); 

DELETE 
FROM customercountry
WHERE idcountry IN (SELECT idcountry 
					 FROM country
					 WHERE countryname='AddCountry'); 
 
DELETE FROM account
WHERE nameaccount='AddAccount';
 
DELETE FROM country
WHERE countryname='AddCountry';
 
DELETE FROM customer
WHERE customername='AddCustomer';

DELETE FROM region
WHERE nameregion='AddRegion';

DELETE FROM customerunit
WHERE namecustomerunit='AddUnit';

DELETE FROM region
WHERE nameregion='EditRegion';

DELETE FROM lifecycle
WHERE attributevalue='AddLifecycle';

DELETE FROM lifecycle
WHERE attributevalue='EditLifecycle';

