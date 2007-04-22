ant test-all
cd extras/spring-ajax
ant test
cd ../spring-freemarker
ant test
cd ../spring-velocity
ant test
cd ../jsf
ant test
cd ../struts
ant test
cd ../struts2
ant test
cd ../tapestry
# clear database tables since Tapestry
# defaults to 10 records in contrib:Table
ant -f ../../build.xml clear
ant test
cd ../webwork
ant test
cd ../ibatis
ant test
cd ../jdo
ant test
cd ../ojb
ant test
cd ../spring-jdbc
ant test
cd ../security
ant test