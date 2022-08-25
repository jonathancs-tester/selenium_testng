#!/bin/bash

SVN_DIR=$1
SVN_VERSION=$2
SVN_VERSION_LAST=$3
SVN_VERSION_TAG='https://openalm.lmera.ericsson.se/svnplugin/dama/dama-home/'$SVN_DIR'/dama-version-'$SVN_VERSION

svn ls $SVN_VERSION_TAG >& saida.out
if [ `cat saida.out | grep "don't exist" | wc -l` -eq 1 ] 
then
  mkdir /opt/ericsson/dama/apache/www/html/testlinkpdf/nightlybuild/admin/$SVN_VERSION /opt/ericsson/dama/apache/www/html/testlinkpdf/nightlybuild/view/$SVN_VERSION
  chmod +rw /opt/ericsson/dama/apache/www/html/testlinkpdf/nightlybuild/admin/$SVN_VERSION /opt/ericsson/dama/apache/www/html/testlinkpdf/nightlybuild/view/$SVN_VERSION
  chown tomcat:tomcat /opt/ericsson/dama/apache/www/html/testlinkpdf/nightlybuild/admin/$SVN_VERSION /opt/ericsson/dama/apache/www/html/testlinkpdf/nightlybuild/view/$SVN_VERSION
  sed -i 's,version='$SVN_VERSION_LAST',version='$SVN_VERSION',g' /opt/ericsson/dama/jenkins/jobs/DAMA-TEST-TESTLINK/workspace/src/test/resources/config.properties
  sed -i 's,build=Release,build=Nightly build,g' /opt/ericsson/dama/jenkins/jobs/DAMA-TEST-TESTLINK/workspace/src/test/resources/config.properties
  sed -i 's,pathPDFAdmin=/opt/ericsson/dama/apache/www/html/testlinkpdf/release/admin/'$SVN_VERSION_LAST',pathPDFAdmin=/opt/ericsson/dama/apache/www/html/testlinkpdf/nightlybuild/admin/'$SVN_VERSION',g' /opt/ericsson/dama/jenkins/jobs/DAMA-TEST-TESTLINK/workspace/src/test/resources/config.properties
  sed -i 's,pathPDFView=/opt/ericsson/dama/apache/www/html/testlinkpdf/release/view/'$SVN_VERSION_LAST',pathPDFView=/opt/ericsson/dama/apache/www/html/testlinkpdf/nightlybuild/view/'$SVN_VERSION',g' /opt/ericsson/dama/jenkins/jobs/DAMA-TEST-TESTLINK/workspace/src/test/resources/config.properties
  sed -i 's,pathTestlink=http://146.250.180.193/testlinkpdf/release/,pathTestlink=http://146.250.180.193/testlinkpdf/nightlybuild/,g' /opt/ericsson/dama/jenkins/jobs/DAMA-TEST-TESTLINK/workspace/src/test/resources/config.properties
  rm saida.out
else
  mkdir /opt/ericsson/dama/apache/www/html/testlinkpdf/release/admin/$SVN_VERSION /opt/ericsson/dama/apache/www/html/testlinkpdf/release/view/$SVN_VERSION
  chmod +rw /opt/ericsson/dama/apache/www/html/testlinkpdf/release/admin/$SVN_VERSION /opt/ericsson/dama/apache/www/html/testlinkpdf/release/view/$SVN_VERSION
  chown tomcat:tomcat /opt/ericsson/dama/apache/www/html/testlinkpdf/release/admin/$SVN_VERSION /opt/ericsson/dama/apache/www/html/testlinkpdf/release/view/$SVN_VERSION
  sed -i 's,version='$SVN_VERSION_LAST',version='$SVN_VERSION',g' /opt/ericsson/dama/jenkins/jobs/DAMA-TEST-TESTLINK/workspace/src/test/resources/config.properties
  sed -i 's,build=Nightly build,build=Release,g' /opt/ericsson/dama/jenkins/jobs/DAMA-TEST-TESTLINK/workspace/src/test/resources/config.properties
  sed -i 's,pathPDFAdmin=/opt/ericsson/dama/apache/www/html/testlinkpdf/nightlybuild/admin/'$SVN_VERSION_LAST',pathPDFAdmin=/opt/ericsson/dama/apache/www/html/testlinkpdf/release/admin/'$SVN_VERSION',g' /opt/ericsson/dama/jenkins/jobs/DAMA-TEST-TESTLINK/workspace/src/test/resources/config.properties
  sed -i 's,pathPDFView=/opt/ericsson/dama/apache/www/html/testlinkpdf/nightlybuild/view/'$SVN_VERSION_LAST',pathPDFView=/opt/ericsson/dama/apache/www/html/testlinkpdf/release/view/'$SVN_VERSION',g' /opt/ericsson/dama/jenkins/jobs/DAMA-TEST-TESTLINK/workspace/src/test/resources/config.properties
  sed -i 's,pathTestlink=http://146.250.180.193/testlinkpdf/nightlybuild/,pathTestlink=http://146.250.180.193/testlinkpdf/release/,g' /opt/ericsson/dama/jenkins/jobs/DAMA-TEST-TESTLINK/workspace/src/test/resources/config.properties
  rm saida.out
fi
  
