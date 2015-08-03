################################################################
# 0. Prerequisite
################################################################

- Java 
sudo apt-get install openjdk-6-jdk

- VPNC
sudo apt-get install vpnc

- JSVC
sudo apt-get install jsvc


################################################################
# 1. Connect to amazon server
################################################################

ssh -i hieu_micro_instance.pem ubuntu@54.254.216.92


################################################################
# 2. Connect to VNA VPN
################################################################

- Manual

sudo vpnc ./AITSVPN.conf
sudo vpnc-disconnect

- Crontab every 5 minutes

*/5 * * * * sudo /home/ubuntu/sabre/sabre-vpn.sh


################################################################
3. Run App
################################################################

cd ~/sabre

./sabre.sh (start|stop|restart)


################################################################
# 4. Logging
################################################################

cd /tmp/logs/sabre

- sabre-vpn.log : VPN connection log

- sabre.log     : App log