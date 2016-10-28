JBOSS_HOME=/u01/WEBTT_QLCP/jboss-eap-6.1.0
echo "Deploy ear-assembly.ear"
$JBOSS_HOME/bin/jboss-cli.sh --connect --controller=10.151.70.120 --file=deploy_app.cli
echo "Deploy vms-web.ear"
$JBOSS_HOME/bin/jboss-cli.sh --connect --controller=10.151.70.120 --file=deploy_web.cli
echo "Done!"
