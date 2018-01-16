/* Typical pin layout used:
 * -----------------------------------------------------------------------------------------
 *             MFRC522      Arduino       Arduino   Arduino    Arduino          Arduino
 *             Reader/PCD   Uno/101       Mega      Nano v3    Leonardo/Micro   Pro Micro
 * Signal      Pin          Pin           Pin       Pin        Pin              Pin
 * -----------------------------------------------------------------------------------------
 * RST/Reset   RST          9             5         D9         RESET/ICSP-5     RST
 * SPI SS      SDA(SS)      10            53        D10        10               10
 * SPI MOSI    MOSI         11 / ICSP-4   51        D11        ICSP-4           16
 * SPI MISO    MISO         12 / ICSP-1   50        D12        ICSP-1           14
 * SPI SCK     SCK          13 / ICSP-3   52        D13        ICSP-3           15
 */




#include <MFRC522.h>

#include<SPI.h>
//#include <SoftwareSerial.h>
//SoftwareSerial bt(1,0);
//RFID pins 
int mipin=11; // ICSP-4 MOSI:11
int mopin =12; // ICSP-1 MISO: 12
int iscppin= 13; // ISCP-3 SCK :13
int sspin= 10; //(Configurable) SS :10
int resetpin= 9; //(Configurable) RST :9

//Bluetooth pins
//int txpin=1; //rx pin of bluetooth
//int rxpin=0; // tx pin of bluetooth
String rfidcode[20];
MFRC522 mfrc522(sspin, resetpin);  // Create MFRC522 instance

//MFRC522::MIFARE_Key key; 
byte nuidPICC[4];    // storing uid code 
int led1=7,led2=8;
#define buzzer1 A0
//simple database for checking the item and it's price
//struct database
//{
// byte uid[4];
// char itemname[20];
// int price; 
//}variable[10];


//void senddata(int i)
//{
//  bt.print(variable[i].itemname);
//  bt.print(variable[i].price);
//    Serial.print(variable[i].itemname);
//  Serial.print(variable[i].price);
//}

void setup() {
  Serial.begin(9600);   // Initialize serial communications with the PC
  SPI.begin();      // Init SPI bus
  mfrc522.PCD_Init();   // Init MFRC522
 pinMode(led1,OUTPUT);
  pinMode(led2,OUTPUT);
  pinMode(buzzer1,OUTPUT);
}

//void assign_value(int k)                     // values for each item is to stored as per item name and price
//{
// 
//  variable[k].uid.uidbyte[0]=0x55;
//  variable[k].uid.uidbyte[1]=0x4B;
//  variable[k].uid.uidbyte[2]=0xD4;
//  variable[k].uid.uidbyte[3]=0x65;
//    strcpy(variable[k].itemname,"First item");
//variable[k].price=120;
//}
////55 4B D4 65
void loop()
{
  digitalWrite(led1,LOW);
  digitalWrite(led2,LOW);
  digitalWrite(buzzer1,LOW);
  // Look for new cards
  if ( ! mfrc522.PICC_IsNewCardPresent()) {
    return;
  }

  // Select one of the cards
  if ( ! mfrc522.PICC_ReadCardSerial()) {
    return;
  }
 for (byte i = 0; i < 4; i++) {
      nuidPICC[i] = mfrc522.uid.uidByte[i];
    }
    delay(2000);
    sendtag();
}

void sendtag()
{
  for(byte i=0;i<4;i++)
  Serial.print(nuidPICC[i],HEX);
  digitalWrite(led1,HIGH);
  digitalWrite(led2,LOW);
  digitalWrite(buzzer1,HIGH);
  delay(1000);
}
//void itemcheck()
//{
//  for(int i=0; i<10; i++)
//     if(variable[i].uid.uidbyte[0]==nuidPICC[0]&&
//     variable[i].uid.uidbyte[1]==nuidPICC[1]&&
//     variable[i].uid.uidbyte[2]==nuidPICC[2]&&
//     variable[i].uid.uidbyte[3]==nuidPICC[3])
//     Serial.println("ITEM FOUND");
//      senddata(i); 
//       
//}
