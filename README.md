# 2020_1_Capstone
해당 프로젝트는 ‘창업캡스톤디자인 : 대학교 캠퍼스 화폐 시스템 및 대학생 학업 활동 포인트 활용 인프라 구축’으로
‘대학교 캠퍼스의 경제 활성화를 위해 캠퍼스화폐를 만들면 어떨까?’라는 아이디어와 최근에 가장 핫한 ‘모여봐요 동물의 숲’이라는 게임의 현실반영 시스템을 더해 하나의 화폐 결제 시스템을 고안하는 프로젝트이다.
![설계도](https://user-images.githubusercontent.com/44914802/87288390-3e28f980-c536-11ea-947a-8b0b59981f0c.png)
# 백앤드
## 시스템 구성도
![시스템구성도](https://user-images.githubusercontent.com/44914802/87288393-3f5a2680-c536-11ea-9930-2040f30aab67.png)
실제 2주라는 시간동안 시연할 어플리케이션을 제작하기에는 제작기간이 매우 짧기 때문에 실제 서버를 사용하지 않고 어플리케이션 내의 기능만 사용하는 어플리케이션을 제작하는 방향으로 설계하였다.  
해당 프로젝트에서 사용하는 DBMS는 Kotlin 내에 존재하는 SQLite를 사용하였으며 총4개의 테이블이 존재한다.  
<b>1.	사용자들의 목록을 담을 USER
2.	모은 화폐로 구매한 기프티콘과 같은 상품들을 저장할 GIFT
3.	구매처에서 구매가능한 상품들의 목록을 담을 SHOP
4.	프로젝트의 게임이론에 사용할 GAME</b>

사용자는 출석과 같은 학교생활을 하며 포인트(화폐)를 모으면 모은 포인트를 구매처(상점)에서 사용하거나 메인화면에 있는 캐릭터를 키우기 위한 아이템들을 구매할 수 있다.
시연 어플리케이션은 총 6개의 Activity로 구성되어 있으며 SQLite를 다루기 위한 Activity 1개와 5개의 화면 Activity가 존재한다.
### 1.	MainActivity
해당 프로젝트에서 사용할 게임의 배경이 된 게임은 ‘다마고치’이다. 실질적으로 게임을 도입하는 이유는 화폐시스템을 사용하게 하기 위한 일종의 동기가 되어야 하는데 무언가를 키우는 게임이 이에 적합하다고 생각했기 때문이다. 사용하기 편하도록 상단에 구매처, 보관함, 출석현황을 볼 수 있는 버튼을 작게 배치하였고 가운데에 캐릭터를 배치하였다. 여기서 캐릭터는 1학년부터 졸업하는 4학년까지의 일대기를 나타낼 캐릭터로 캠퍼스에 따라 동물이 다르기 때문에 1학년은 알로 설정하였다.  
![main](https://user-images.githubusercontent.com/44914802/87288396-408b5380-c536-11ea-9330-199621e91e1c.png)

### 2.	ShopActivity
실제 구매처에 해당하는 화면으로 SHOP 테이블의 상품목록들을 RecyclerView를 사용함으로 상품의 추가 및 변경, 삭제하기 간편하도록 만들었다. 앞으로의 GameActivity, GiftActivity, AttendActivity들 역시 RecyclerView를 사용하였다.  
![shop](https://user-images.githubusercontent.com/44914802/87288406-41bc8080-c536-11ea-8638-946ff3e5581c.png)

### 3.	GameActivity
캐릭터에 사용할 아이템들을 구매할 수 있는 GameActivity이다. 제작 당시에는 시연단계라 배고픔수치와 먹이주기만 만들어 놓았지만 GAME테이블과 RecyclerView를 사용했기 때문에 수치 추가와 아이템 추가 및 수정이 매우 간편하다.  
![game](https://user-images.githubusercontent.com/44914802/87288409-42edad80-c536-11ea-85f7-3d7909aa2d1e.png)

### 4.	GiftActivity
ShopActivity에서 구매한 상품들을 나타내는 GiftActivity이다. Shop에서 구매버튼을 누르면 GIFT테이블에 INSERT하는 방식으로 구현하였고 사용기한이 들어갈 자리에 구매한 시간을 저장하도록 만들었다.  
![gift](https://user-images.githubusercontent.com/44914802/87288410-441eda80-c536-11ea-891e-4a2ee9dcc45b.png)

### 5.	AttendActivity
현재 출석현황을 확인할 수 있는 화면으로 설계는 캠퍼스내에 사용하는 데이터베이스에서 출석현황을 불러와야 하지만 시연당시에는 출석상태를 고정값으로 설정해주었다.  
![attend](https://user-images.githubusercontent.com/44914802/87288411-44b77100-c536-11ea-9e43-e4decc460558.png)
