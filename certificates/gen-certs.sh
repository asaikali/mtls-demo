#source https://github.com/joutwate/mtls-springboot

CLIENT_KEYSTORE=client.jks
SERVER_KEYSTORE=server.jks

# Generate a client and server RSA 2048 key pair
keytool -genkeypair -alias client -keyalg RSA -keysize 2048 -dname "CN=Client,OU=Client,O=Example,L=Toronto,S=ON,C=CA" -keypass changeme -keystore $CLIENT_KEYSTORE -storepass changeme
keytool -genkeypair -alias server -keyalg RSA -keysize 2048 -dname "CN=Server,OU=Server,O=Example,L=Toronto,S=ON,C=CA" -keypass changeme -keystore $SERVER_KEYSTORE -storepass changeme

# Export public certificates for both the client and server
keytool -exportcert -alias client -file client-public.cer -keystore $CLIENT_KEYSTORE -storepass changeme
keytool -exportcert -alias server -file server-public.cer -keystore $SERVER_KEYSTORE -storepass changeme

# Import the client and server public certificates into each others keystore
keytool -importcert -keystore $CLIENT_KEYSTORE -alias server-public-cert -file server-public.cer -storepass changeme -noprompt
keytool -importcert -keystore $SERVER_KEYSTORE -alias client-public-cert -file client-public.cer -storepass changeme -noprompt