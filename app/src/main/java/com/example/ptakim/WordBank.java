package com.example.ptakim;

import java.util.Random;

public class WordBank {
    private static boolean[] Categories;
    private static int NumOfCategories;
    private static int WordsInCategory;
    private static int NumOfWords;
    private static String[][] Words =
            {
                    //מקומות
                    {"ים המלח", "הבית הלבן", "יפן", "אילת", "הקולסאום", "ישראל ", "סקוטלנד", "מאדים", "גבעת התחמושת", "כיכר רבין", "הכנרת", " ארגנטינה ", "ברצלונה", "החומה הסינית", "ירושליים", "מוזאון הלובר ", "קיסריה", " הירח", "מגדל פיזה", "רצועת עזה ", "הקריה", "מוזאון ישראל ", "הוותיקן"},
                    //סרטים סדרות וספרים
                    {"דונאלד דאק ","משפחת קדמוני","שבי השבלול ","דרדסבא ","הגוקר","צבי הנינג'ה","מיקי מאוס","סיינפלד","חלף עם הרוח"," מד מן","צופן דה וינצי ","סטאר טרק","להציל את טוראי ראיין","בנות גילמור","המדריך לטרמפיסט הגלקסיה ","האוס","פו הדב","משחקי הכס","המיניונים","הנוסע השמיני","לוליטה","הדרדסים","בארט סימפסון","מחוברים"},
                    //חברות ואירגונים
                    {"פאלפל כדורי","הבית של חומוס","מקורות","זארה","האו","טויוטה","מכון","ויצמן","גוגל","מעריב","איקאה","אמזון","יוטיוב","נייק","אאודי","וויז","וואטסאפ","גלגל'צ","פולסווגן","מיקרוסופט","סטימצקי","פולסווגן","רמי לוי","קינלי","אלדן ","נטפליקס","צהל","סטארבקס","האוניברסיטה העיברית","שילב"," נוקיה","סקייפ","הייניקן","פפסי"},
                    //חפצים ומושגים
                    {"בגרות","רוח הקודש","ארץ עיר","האנגאובר","טלפתיה","סיירת מטכ'ל","צלילה","גילוי אמריקה","גובלין","ווי פיי","סמוראי","מחשב נייד","כרטיס אשראי","שרירי בטן","ריקוד סלסה","מבוך","פומפיה","דינאמיט","לייק","באג אלפיים","קלסטרופוביה","בום על-קולי","המונה ליזה","טבעת נישואים","תואר ראשון","הרנסאנס","החומה הסינית","כוח המשיכה","פאי"},
                    //אנשי תרבות ובידור
                    {"יו 2","ארז טל","הריסון פורד","ג'ון לנון","מרלין מונרו","סטינג","שרית חדד","אלביס פרסלי","סנדרה בולוק","נטלי פורמן","ביטלס","ספייס גירלס","עמוס עוז","מדונה","ג'וליה רוברטס","פבלו פיקאסו","ג'ון לנון"," משינה ","מטאליקה ","דיוויד בואי","וויטני יוסטון","נירוונה","ג'סטין טימברלייק","טום האנקס ","אופרה ווינפרי"},
                    //ספורט
                    {"טריאתלון","שאקיל אוניל","מכבי תל אביב","הוקי","ערן זהבי","קפיצה לרוחק","קארים עבדול ג'באר","ניב ריסקין","ריאל מדריד","יוסי אבוקסיס","יוסיין בולט","אבי נימני","אריק זאבי","פורמולה-1","ניו-יורק ניקס","חיים רביבו","טל ברודי","מארדונה","לברון ג'יימס","קריקט","הטלת קידון","הדיפת כדור ברזל","קובי בריאנט","רפאל נדל","מייקל שומאכר","מוחמד עלי"},
                    //טבע
                    {"המזרח הרחוק","צונמי","דלעת","דורבן","שושנת ים","נדל","איגואנה","חסרי חוליות","פינצ'ר","קיפוד ים","סרטן","לוויתן","נענע","בבון","ממותה","זברה","גויאבה","פלפל חריף","פפריקה","טיגריס","נקר","כלניות","קפה","גבעול","קשיו","צב","כינים","זאב","צנון","ציפור גן-עדן","פרוקי רגליים","אלמוג","דוכיפת","צ'יוואווה","אגוז","שפן","אפרסק"},
                    //מדענים
                    {"דלאי לאמה","דונלד טראמפ","המלכה אליזבת","סטיב ג'ובס","הילרי קלינטון","ארכימדס","ג'וסף סטאלין","אפלטון","וולדימיר פוטין","הנרי פורד","רנה דקארט","לואי פסטר","מאו דזה דונג","מרטין לות'ר קינג","אריסטו","תות ענח אמון","נפולאון בונפרטה","גלילאו גליליי","אמא תרזה","חוסני מוברק","אברהם לינקולן","ישו","ג'וסף סטאלין","מארק צוקרברג","אברהם אבינו","ניל אמסטרונג","יוליוס קיסר","ג'ון קנדי","בניימין זאב הרצל"},
            };



    /*String[] place = new String[20];//מקומות
    String[] movie = new String[20];//סרטים סדרות וספרים
    String[] company = new String[20];//חברות ואירגונים
    String[] object = new String[20];//חפצים ומושגים
    String[] culture_leaders = new String[20];//אנשי תרבות ובידור
    String[] sport = new String[20];//ספורט
    String[] nature = new String[20];//טבע
    String[] scientists = new String[20];//מדענים
    */

    public WordBank(int NumOfWords,int NumOfCategories,boolean[] Categories){
        this.Categories = Categories;
        this.NumOfCategories = NumOfCategories;
        this.NumOfWords = NumOfWords;
        WordsInCategory = NumOfWords/NumOfCategories;
    }


    public static String[][] WordsOfCategory() {
        String[][] selectedWords = new String[8][];
        int index = 0;
        for(int i = 0; i< 8 ; i++){
            if(Categories[i]){
                if(NumOfCategories != 1){
                    selectedWords[i] = selectWordsInCategory(i,WordsInCategory);
                    NumOfWords = NumOfWords - WordsInCategory;
                    NumOfCategories --;
                }
                else{
                    selectedWords[i] = selectWordsInCategory(i,NumOfWords);
                }
            }
        }
        return selectedWords;
    }

    public static String [] selectWordsInCategory(int i, int Number){
        //selecting a Number of words from i category
        int [] indexOfWords = new int[Number];
        int t = 0;
        int r =0;
        String [] ans = new String[Number];
        boolean goodRandomNum = false;
        for(;t<Number;t++){
            while (!goodRandomNum) {
                r = random(0, Words[i].length);
                goodRandomNum = true;
                for(int m = 0; m< t & goodRandomNum;m++){
                    if(r == indexOfWords[m])
                        goodRandomNum = false;
                }
            }
            indexOfWords[t] = r;
            ans[t] = Words[i][r];
            goodRandomNum = false;
        }
        return ans;
    }

    public static int random(int low, int high){
        Random r = new Random();
        int result = r.nextInt(high-low) + low;
        return  result;
    }
}


