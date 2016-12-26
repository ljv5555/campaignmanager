# campaignmanager

* Files found here include a Database Schema design (db_schema_info.txt). From that, I made simple POJOs and used Grails to generate the basic CRUD skeleton.

* Under the src folder, ChooserImpl is the old implementation, ChooserImpl2 wraps the 
apache implementation. 

* test1 and test2 validate the old and new implementations have results where    (expected-25%) &lt; actual &lt; (expected+25%)

* the other annotated tests show the relative times taken with various collection sizes:

JUnit output: 
<pre>
v1-collection size: 10, average choice time (ms): 2.766598
v1-collection size: 20, average choice time (ms): 6.615277
v1-collection size: 30, average choice time (ms): 11.230195
v1-collection size: 40, average choice time (ms): 18.997164
v1-collection size: 50, average choice time (ms): 29.341262
v1-collection size: 60, average choice time (ms): 44.184475
v1-collection size: 70, average choice time (ms): 60.147614999999995
v1-collection size: 80, average choice time (ms): 78.123491
v2-collection size: 10, average choice time (ms): 22.71327
v2-collection size: 20, average choice time (ms): 22.575396
v2-collection size: 30, average choice time (ms): 23.020514
v2-collection size: 40, average choice time (ms): 22.687687999999998
v2-collection size: 50, average choice time (ms): 22.654723999999998
v2-collection size: 60, average choice time (ms): 22.997424
v2-collection size: 70, average choice time (ms): 23.033338
v2-collection size: 80, average choice time (ms): 23.382531999999998
</pre>
