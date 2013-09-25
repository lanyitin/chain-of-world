package tw.edu.niu.pet.work;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteDB extends SQLiteOpenHelper {
	private final static String DB_Name = "EC";
	private final static int DB_Ver = 1;
	private final static String[] Table_Name = {"exam_1", "exam_2", "exam_3", "exam_4", "exam_5", "exam_6"};
	private final static String Field_1 = "_id";
	private final static String Field_2 = "question";
	private final static String Field_3 = "opt_a";
	private final static String Field_4 = "opt_b";
	private final static String Field_5 = "opt_c";
	private final static String Field_6 = "opt_d";
	private final static String Field_7 = "ans";
	private final static String Field_8 = "knowledge";

	public SQLiteDB(Context context) {super(context, DB_Name, null, DB_Ver);}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String[] create_sql = {"CREATE TABLE " + Table_Name[0] + "(" 
				+ Field_1 + " INTEGER PRIMARY KEY, " + Field_2 + " TEXT, " 
				+ Field_3 + " TEXT, " + Field_4 + " TEXT, " 
				+ Field_5 + " TEXT, " + Field_6 + " TEXT, " 
				+ Field_7 + " TEXT," + Field_8 + " TEXT);",
				
				"CREATE TABLE " + Table_Name[1] + "(" 
				+ Field_1 + " INTEGER PRIMARY KEY, " + Field_2 + " TEXT, " 
				+ Field_3 + " TEXT, " + Field_4 + " TEXT, " 
				+ Field_5 + " TEXT, " + Field_6 + " TEXT, " 
				+ Field_7 + " TEXT," + Field_8 + " TEXT);", 
				"CREATE TABLE " + Table_Name[2] + "(" 
				+ Field_1 + " INTEGER PRIMARY KEY, " + Field_2 + " TEXT, "
				+ Field_3 + " TEXT, " + Field_4 + " TEXT, "
				+ Field_5 + " TEXT, " + Field_6 + " TEXT, "
				+ Field_7 + " TEXT," + Field_8 + " TEXT);", 
				"CREATE TABLE " + Table_Name[3] + "(" 
				+ Field_1 + " INTEGER PRIMARY KEY, " + Field_2 + " TEXT, "
				+ Field_3 + " TEXT, " + Field_4 + " TEXT, "
				+ Field_5 + " TEXT, " + Field_6 + " TEXT, "
				+ Field_7 + " TEXT," + Field_8 + " TEXT);", 
				"CREATE TABLE " + Table_Name[4] + "(" 
				+ Field_1 + " INTEGER PRIMARY KEY, " + Field_2 + " TEXT, "
				+ Field_3 + " TEXT, " + Field_4 + " TEXT, "
				+ Field_5 + " TEXT, " + Field_6 + " TEXT, "
				+ Field_7 + " TEXT," + Field_8 + " TEXT);", 
				"CREATE TABLE " + Table_Name[5] + "(" 
				+ Field_1 + " INTEGER PRIMARY KEY, " + Field_2 + " TEXT, "
				+ Field_3 + " TEXT, " + Field_4 + " TEXT, "
				+ Field_5 + " TEXT, " + Field_6 + " TEXT, "
				+ Field_7 + " TEXT," + Field_8 + " TEXT);"};
 
		db.execSQL(create_sql[0]);
		db.execSQL(create_sql[1]);
		db.execSQL(create_sql[2]);
		db.execSQL(create_sql[3]);
		db.execSQL(create_sql[4]);
		db.execSQL(create_sql[5]);
		
		/** NUCLEAR FINISH */
		String exam1_1 = "insert into " + Table_Name[0] + " values (1, '請問核能發電廠是否屬於可再生能源發電廠的一種?', '是，核能和太陽能一樣都是再生能源', '是，核能和風力一樣都是再生能源', '不是', '不一定', 'C', '核能是不可再生的能源之一，再生能源有水力、風力、潮汐、太陽能喔！')";
		String exam1_2 = "insert into " + Table_Name[0] + " values (2, '請問目前核能發電原理主要是利用何種反應所釋放的能量產生電能?', '核分裂', '核融合(核聚變)', '都有', '以上皆非', 'A', '核能發電是利用核分裂或核聚變反應所釋放的能量產生電能，但由於控制核融合的技術障礙，目前運轉中的核能發電廠都是利用核分裂反應而發電喔！')";
		String exam1_3 = "insert into " + Table_Name[0] + " values (3, '請問目前核能發電廠使用的核燃料一般是_____?', '鈾-234', '鈾-235', '鎂-235', '鈾-238', 'B', '目前核能發電廠使用的核燃料主要是鈾-235喔！')";
		String exam1_4 = "insert into " + Table_Name[0] + " values (4, '請問鈾(U)在自然界中含量最多的是_____?', '鈾-234', '鈾-235', '鈾-238', '以上皆非', 'C', '自然界中含量最多的是鈾-238喔！')";
		String exam1_5 = "insert into " + Table_Name[0] + " values (5, '請問核能發電為何不能使用鈾-238?', '因為鈾-238在自然界中含量稀少', '鈾-238被快中子碰撞後會吸收其能量，使得快中子不能進一連鎖反應', '鈾-238是非常不穩定的物質', '鈾-238放射性遠高於鈾-235', 'B', '鈾-238是自然界含量最多的鈾元素，被快中子碰撞後會吸收其能量，使得快中子不能進一連鎖反應，是非常穩定的物質，放射性遠低於鈾-235喔！')";
		String exam1_6 = "insert into " + Table_Name[0] + " values (6, '請問核能發電主要是以_____撞擊鈾元素?', '質子', '中子', '電子', '原子', 'B', '核能發電主要是透過慢中子撞擊鈾元素喔！')";
		String exam1_7 = "insert into " + Table_Name[0] + " values (7, '請問核能發電所產生的電力可以提供一般民眾做_____?', '使用烤麵包機烤土司', '使用吹風機吹乾頭髮', '與家人一起觀賞綜藝節目', '上網瀏覽Facebook訊息', 'ABCD', '核能發電所產生的電力可以供應一般民生用電喔！')";
		String exam1_8 = "insert into " + Table_Name[0] + " values (8, '請問下列哪些是歷史上重大的核電廠事故?', '車諾比核事故', '三哩島核洩漏事故', '福島第一核電廠事故', '以上皆是', 'D', '車諾比核事故（1986年）三哩島核洩漏事故（1979年）福島第一核電廠事故（2011年）都是歷史上重大的核電廠事故喔！')";
		String exam1_9 = "insert into " + Table_Name[0] + " values (9, '請問造成福島第一核電廠事故的原因包含_____，所以我們更需要有專業的技術人員來嚴格把關。', '地震', '海嘯', '政策', '以上皆是', 'C', '福島第一核電廠事故的原因主要是因為地震造成的海嘯沖毀核電廠設施，並且由於相關人員遲遲未下定決心引入海水以冷卻設備而造成後續輻射傷害。')";
		String exam1_10 = "insert into " + Table_Name[0] + " values (10, '請問哪些是核能發電可能的缺點?', '空氣汙染', '噪音', '輻射廢棄物', '水汙染', 'C', '核能發電過程中產生的核廢料具有輻射性，所以我們更需要良好的設備與專業的技術人員來嚴格控管喔！')";
		
		db.execSQL(exam1_1);
		db.execSQL(exam1_2);
		db.execSQL(exam1_3);
		db.execSQL(exam1_4);
		db.execSQL(exam1_5);
		db.execSQL(exam1_6);
		db.execSQL(exam1_7);
		db.execSQL(exam1_8);
		db.execSQL(exam1_9);
		db.execSQL(exam1_10);
		
		/** FIRE FINISH */
		String exam2_1 = "insert into " + Table_Name[1] + " values (1, '火力發電廠主要是通過燃燒以下何者來進行發電?', '煤', '天然氣', '石油', '以上皆是', 'D', '火力發電廠是通過燃燒煤、天然氣、石油進行發電喔！')";
		String exam2_2 = "insert into " + Table_Name[1] + " values (2, '下列何項能量傳遞是火力發電?', '化學能→機械能→電能', '化學能→熱能、機械能→電能', '化學能→電能', '以上皆非', 'B', '火力發電是通過燃燒煤、石油、天然氣產生能量（化學能）來驅動發電機運轉（機械能）進行發電（電能），過程中會有部分能量損失（熱能）喔！')";
		String exam2_3 = "insert into " + Table_Name[1] + " values (3, '下列何項火力發電的產物是造成溫室效應最主要的原因?', '水氣(H2O)', '二氧化碳(CO2)', '氮氣(N2)', '以上皆非', 'B', '火力發電的過程中，燃燒煤、天然氣、石油會產生大量的二氧化碳喔！')";
		String exam2_4 = "insert into " + Table_Name[1] + " values (4, '下列何項火力發電的產物是造成酸雨最主要的原因?', '二氧化硫(SO2)', '二氧化碳(CO2)', '水氣(H2O)', '氮氧化物(NO,NO2…)', 'AD', '二氧化硫、氮氧化物皆是造成酸雨最主要的原因喔！')";
		String exam2_5 = "insert into " + Table_Name[1] + " values (5, '下列何項是火力發電機組常見的類型?', '蒸汽發電', '燃氣渦輪發電', '往複式發動機', '汽電共生', 'ABCD', '蒸汽發電、燃氣渦輪發電、往複式發動機、汽電共生皆是火力發電機組常見的類型喔！')";
		String exam2_6 = "insert into " + Table_Name[1] + " values (6, '請問台灣目前有幾座火力發電廠(包含離島)?', '7', '8', '9', '10', 'D', '(1)深澳發電廠\n(2)協和發電廠\n(3)林口發電廠\n(4)通霄發電廠\n(5)台中發電廠\n(6)興達發電廠\n(7)大林發電廠\n(8)南部發電廠\n(9)澎湖發電廠\n(10)尖山發電廠\n')";
		String exam2_7 = "insert into " + Table_Name[1] + " values (7, '下列何項可以作為化石能源的替代物?', '核能', '水力', '風力', '以上皆是', 'D', '煤、天然氣、石油皆是數量有限的石化資源，核能、水力、風力等發電方式皆能替代火力喔！')";
		String exam2_8 = "insert into " + Table_Name[1] + " values (8, '請問火力發電所產生的電力可以提供民眾從事何項行為?', '使用吹風機吹乾頭髮', '和家人一起看綜藝節目', '使用電腦上網', '以上皆是', 'D', '火力發電所產生的電力可以供應一般民生用電喔！')";
		String exam2_9 = "insert into " + Table_Name[1] + " values (9, '請問台灣目前最主要的發電方式是火力發電嗎?', '錯，台灣四面環海，應該是水力發電', '錯，核能產生的電力最高，應該是核能發電', '對，是火力發電沒錯', '以上皆非', 'C', '台灣目前最主要的發電方式是火力發電，採用進口石油作為燃料喔！')";
		String exam2_10 = "insert into " + Table_Name[1] + " values (10, '請問燃煤發電，每單位電量所產生的二氧化碳(CO2)排放量為多少?(g CO2/kWhe)', '701', '801', '901', '1001', 'D', '燃煤發電，每單位電量所產生的二氧化碳排放量為1001（g CO2/kWhe）喔！')";
		
		db.execSQL(exam2_1);
		db.execSQL(exam2_2);
		db.execSQL(exam2_3);
		db.execSQL(exam2_4);
		db.execSQL(exam2_5);
		db.execSQL(exam2_6);
		db.execSQL(exam2_7);
		db.execSQL(exam2_8);
		db.execSQL(exam2_9);
		db.execSQL(exam2_10);
		
		/** WATER FINISH */
        String exam3_1 = "insert into " + Table_Name[2] + " values (1, '請問水力發電廠是否屬於可再生能源發電廠的一種?', '是，水力和火力都是屬於可再生的能源', '是，水力和潮汐都是屬於可再生的能源', '不是', '不一定', 'B', '水力、風力、潮汐、太陽能皆屬於可再生的能源喔！')";
        String exam3_2 = "insert into " + Table_Name[2] + " values (2, '請問水力發電的能量轉換有哪些形式?', '水的位能(勢能)轉為機械能，再轉為電能', '水的熱能轉為機械能，再轉為電能', '水的動能轉為機械能，再轉為電能', '水的力學能轉為機械能，再轉為電能', 'ACD', '水力發電廠是利用水的高低差（位能）來驅動發電機運轉（機械能）進行發電（電能）喔！')";
        String exam3_3 = "insert into " + Table_Name[2] + " values (3, '請問在面對溫室氣體過度排放的現今社會，為何已開發國家目前都會優先選擇發展水力發電技術?', '水力發電是唯一已發展成熟的技術', '水力發電是可以大規模開發的可再生資源', '水力發電的成本相較其他發電方式低廉', '水力發電不需要過多的技術人員進行維護工作', 'ABC', '水力發電是目前已發展成熟的技術之一，而且是可以大規模開發的可再生資源，最大的優點是發電的成本相較其他發電方式低廉喔！')";
        String exam3_4 = "insert into " + Table_Name[2] + " values (4, '請問下列何者是水力發電廠的種類?', '堤壩式水力發電廠', '引水式水力發電廠', '潮汐式水力發電廠', '以上皆是', 'D', '堤壩式水力發電廠、引水式水力發電廠、潮汐式水力發電廠皆為水力發電廠的類型喔！')";
        String exam3_5 = "insert into " + Table_Name[2] + " values (5, '請問水力發電廠除了能夠發電供給民眾使用，還有哪些優點(附加價值)?', '控制洪水泛濫', '蓄備灌溉用水', '改善河流航運', '壽命較長', 'ABCD', '水力發電廠的附加價值有控制洪水泛濫、蓄備灌溉用水、改善河流航運、壽命較長喔！')";
        String exam3_6 = "insert into " + Table_Name[2] + " values (6, '請問下列何者是水力發電廠的缺點?', '興建水壩造成生態變遷與土地損失', '水流聲音過大影響附近民眾的生活品質', '水壩若因天災(地震)或人為因素(戰爭)而潰堤，可能會導致嚴重的損失', '水力發電會產生過多的水氣造成氣候變遷', 'AC', '水力發電廠需要興建水壩，但水壩可能會造成生態變遷與土地損失，且若因天災（地震）或人為因素（戰爭）而潰堤，可能會導致嚴重的損失喔！')";
        String exam3_7 = "insert into " + Table_Name[2] + " values (7, '請問水力發電可能產生何種溫室氣體而加劇全球暖化?', '水氣(H2O)', '二氧化碳(CO2)', '甲烷(CH4)', '臭氧(O3)', 'C', '由於水壩需要有相當的深度，在底層容易形成缺氧層，動植物分解後形成甲烷，是一種比二氧化碳強超過30倍的溫室氣體，會加劇全球暖化喔！')";
        String exam3_8 = "insert into " + Table_Name[2] + " values (8, '請問世界上最大的水力發電廠是?', '小浪底水電站', '葛洲壩水電站', '三峽水力發電廠', '伊泰普水力發電廠', 'C', '位於中國的三峽水力發電廠是世界上最大的水力發電廠，水庫容量為393億立方公尺，水庫面積為1084平方公里喔！')";
        String exam3_9 = "insert into " + Table_Name[2] + " values (9, '請問哪些是建置水力發電廠需要因素?', '優秀的技術人員', '合格的設備資源', '水力資源豐富的地區', '以上皆是', 'D', '建置水力發電廠除了要有優秀的技術人員與合格的設備，該地區還需要有豐富的水力資源喔！')";
        String exam3_10 = "insert into " + Table_Name[2] + " values (10, '下列哪些陸地地區可以建置水力發電廠?', '有瀑布的山區', '有潮汐的海邊', '有綠洲的沙漠', '有浮冰的極地', 'AB', '建置水力發電廠的地區需要有豐富的水力資源喔！')";
        
		db.execSQL(exam3_1);
		db.execSQL(exam3_2);
		db.execSQL(exam3_3);
		db.execSQL(exam3_4);
		db.execSQL(exam3_5);
		db.execSQL(exam3_6);
		db.execSQL(exam3_7);
		db.execSQL(exam3_8);
		db.execSQL(exam3_9);
		db.execSQL(exam3_10);
		
		/** WIND FINISH */
        String exam4_1 = "insert into " + Table_Name[3] + " values (1, '請問風力發電廠是否屬於可再生能源發電廠的一種?', '是，風力和太陽能一樣都是再生能源', '是，風力和火力一樣都是再生能源', '不是', '不一定', 'A', '水力、風力、潮汐、太陽能皆屬於可再生的能源喔！')";
        String exam4_2 = "insert into " + Table_Name[3] + " values (2, '請問風力發電為何只能作為輔助發電使用?', '設備耗損速度快', '需要大量的技術人員', '風能無法被控制', '以上皆非', 'C', '風能無法被控制，時而有風時而無風是風力發電最大的缺點，所以風力發電只能作為輔助發電使用喔！')";
        String exam4_3 = "insert into " + Table_Name[3] + " values (3, '請問聯合國曾經通過 _____ 減少溫室氣體的排放，讓風力風電技術成為各國首選的能源發展重點?', '生物多樣性公約', '京都議定書', '多邊環境協議', '以上皆非', 'B', '聯合國曾通過《京都議定書》藉以減少溫室氣體的排放喔！')";
        String exam4_4 = "insert into " + Table_Name[3] + " values (4, '請問風力發電廠除了能夠發電供給民眾使用，還有哪些優點(附加價值)?', '寓教於樂', '觀光休憩', '環境美化', '以上皆是', 'D', '風力發電廠的附加價值有寓教於樂、觀光休憩、環境美化等各項功能喔！')";
        String exam4_5 = "insert into " + Table_Name[3] + " values (5, '下列何者是構成風力發電廠的必要條件?', '發電機', '葉片', '塔架', '以上皆是', 'D', '每座風力發電廠主要都是由發電機、葉片、塔架等三大部分所構成的喔！')";
        String exam4_6 = "insert into " + Table_Name[3] + " values (6, '請問風力發電機運轉的風速應該為多少比較合適(滿載發電)?', '風速越大越好', '風速達每秒6至10公尺', '風速達每秒10至16公尺', '沒有特別規定', 'C', '風力發電機運轉的風速應該為每秒10至16公尺較為理想喔！')";
        String exam4_7 = "insert into " + Table_Name[3] + " values (7, '請問風力發電機是否能夠獨立運轉?', '可以，風力發電機皆可獨立運轉，但每座風力發電機仍不能當作單獨的風力發電廠', '可以，風力發電機皆可獨立運轉，且每座風力發電機均可視為單獨的風力發電廠，是屬於一種分散式發電系統','不可以，風力發電機需要由專業的技術人員統一管理', '不一定', 'B', '風力發電機皆可獨立運轉，且每座風力發電機均可視為單獨的風力發電廠，是屬於一種分散式發電系統喔！')";
        String exam4_8 = "insert into " + Table_Name[3] + " values (8, '請問台灣如果要發展風力發電技術，何處最適合?(不考慮土地資源、人口問題等外在因素)', '台北', '新竹', '高雄', '宜蘭', 'B', '新竹地區多風，所以非常適合建置風力發電廠喔！')";
        String exam4_9 = "insert into " + Table_Name[3] + " values (9, '請問哪些是建置風力發電廠需要克服的問題?', '鳥群', '雷擊', '鹽害', '噪音', 'ABCD', '鳥擊、雷擊、鹽害、噪音是建置風力發電廠需要克服的問題喔！')";
        String exam4_10 = "insert into " + Table_Name[3] + " values (10, '下列哪些陸地地區可以建置風力發電廠?', '山區', '海邊', '沙漠', '極地', 'ABCD', '陸地上所有地形幾乎都可以建置風力發電廠，不過礙於法令與飛安的限制，部分地區雖然風能強勁，但是不能發展喔！')";
		
		db.execSQL(exam4_1);
		db.execSQL(exam4_2);
		db.execSQL(exam4_3);
		db.execSQL(exam4_4);
		db.execSQL(exam4_5);
		db.execSQL(exam4_6);
		db.execSQL(exam4_7);
		db.execSQL(exam4_8);
		db.execSQL(exam4_9);
		db.execSQL(exam4_10);
		
		/** SEA FINISH */
        String exam5_1 = "insert into " + Table_Name[4] + " values (1, '請問潮汐發電是利用_____進行發電?', '潮汐海流的移動', '潮汐海面的升降', '潮汐海流的壓力', '潮汐海流的重力', 'AB', '潮汐發電是利用潮汐海流的移動與潮汐海面的升降進行發電喔！')";
        String exam5_2 = "insert into " + Table_Name[4] + " values (2, '請問潮汐發電主要有哪些形式?', '渦輪式系統', '堰壩式系統', '潮流式系統', '懸吊式系統', 'BC', '潮汐發電主要有堰壩式系統與潮流式系統喔！')";
        String exam5_3 = "insert into " + Table_Name[4] + " values (3, '請問堰壩式系統是利用_____進行發電?', '海水流動的動能', '海水向下流動所產生的壓力', '海水在地球上的萬有引力', '海水潮汐高低差的位能', 'D', '堰壩式系統是利用海水潮汐高低差（位能）進行發電喔！')";
        String exam5_4 = "insert into " + Table_Name[4] + " values (4, '請問潮流式系統是利用_____進行發電?', '海水流動的動能', '海水向下流動所產生的壓力', '海水在地球上的萬有引力', '海水潮汐高低差的位能', 'A', '潮流式系統是利用海水流動（動能）進行發電喔！')";
        String exam5_5 = "insert into " + Table_Name[4] + " values (5, '請問下列哪些是屬於可再生能源?', '太陽能和火力', '水力和潮汐', '核能和風力', '潮汐與地熱', 'BD', '水力、風力、潮汐、太陽能、地熱皆屬於可再生的能源喔！')";
		
		db.execSQL(exam5_1);
		db.execSQL(exam5_2);
		db.execSQL(exam5_3);
		db.execSQL(exam5_4);
		db.execSQL(exam5_5);
		
		/** SUN FINISH */
        String exam6_1 = "insert into " + Table_Name[5] + " values (1, '請問太陽能發電是把_____轉換成電能?', '太陽的光線', '太陽的熱能', '太陽風', '太陽黑子', 'AB', '直接使用：太陽能光伏為光伏光轉換成電流（光電效應）。間接使用：聚光太陽能熱發電為使用透鏡或反射鏡和跟蹤系統將大面積的陽光聚焦成一個小束')";
        String exam6_2 = "insert into " + Table_Name[5] + " values (2, '請問聚光太陽能發電(PV)是使用_____，利用光學原理將大面積的陽光聚焦到一個相對細小的集光區中?', '透鏡', '反射鏡', '追蹤系統', '電池', 'ABC', '聚光太陽能發電是使用透鏡、反射鏡與追蹤系統，利用光學原理將大面積的陽光聚焦到一個相對細小的集光區中喔！')";
        String exam6_3 = "insert into " + Table_Name[5] + " values (3, '請問聚光太陽能發電(PV)所儲存的熱量允許最多幾小時的發電?', '6小時', '12小時', '24小時', '48小時', 'C', '聚光太陽能發電所儲存的熱量最多可以供應24小時的發電喔！')";
        String exam6_4 = "insert into " + Table_Name[5] + " values (4, '請問太陽能發電中的光伏技術(PV)主要是利用_____將光轉換成電流?', '能量守恆', '光電效應', '光的反射定律', '光的折射定律', 'B', '太陽能發電中的光伏技術是利用光電效應將光轉換成電流喔！')";
        String exam6_5 = "insert into " + Table_Name[5] + " values (5, '請問下列哪些是太陽能發電的缺點?', '空氣汙染', '水汙染', '噪音', '產生金屬廢棄物', 'D', '太陽能發電會產生金屬廢棄物喔！')";
		
		db.execSQL(exam6_1);
		db.execSQL(exam6_2);
		db.execSQL(exam6_3);
		db.execSQL(exam6_4);
		db.execSQL(exam6_5);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String drop_table1 = "drop table if exists " + Table_Name[0];
		String drop_table2 = "drop table if exists " + Table_Name[1];
		String drop_table3 = "drop table if exists " + Table_Name[2];
		db.execSQL(drop_table1);
		db.execSQL(drop_table2);
		db.execSQL(drop_table3);
		onCreate(db);
		
	}
	
	public Cursor select(int i){
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.query(Table_Name[i], null, null, null, null, null, null);
		return cursor;
		
	}

}
