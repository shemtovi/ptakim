package com.idanroey.ptakim_2

class Ptakim(filteredCategories: IntArray, private val numberOfWords: Int, private val team1: Team, private val team2: Team) {

    var currentTeam: Team
    val wordsArray: Set<String>
    val noMoreCards: Boolean
        get() = numberOfWords - guessedRight.size == 0
    private var pointer = 0
    private var roundWordList = mutableListOf<String>()
    private var guessedRight = mutableSetOf<String>()
    private val wordBank: Map<Int, List<String>>
        get() = mapOf(
            R.id.places to listOf("ים המלח", "הבית הלבן", "יפן", "אילת", "הקולסאום", "ישראל ", "סקוטלנד", "מאדים", "גבעת התחמושת", "כיכר רבין", "הכנרת", " ארגנטינה ", "ברצלונה", "החומה הסינית", "ירושליים", "מוזאון הלובר ", "קיסריה", " הירח", "מגדל פיזה", "רצועת עזה ", "הקריה", "מוזאון ישראל ", "הוותיקן"),
            R.id.movies to listOf("דונאלד דאק ","משפחת קדמוני","שבי השבלול ","דרדסבא ","הגוקר","צבי הנינג'ה","מיקי מאוס","סיינפלד","חלף עם הרוח"," מד מן","צופן דה וינצי ","סטאר טרק","להציל את טוראי ראיין","בנות גילמור","המדריך לטרמפיסט הגלקסיה ","האוס","פו הדב","משחקי הכס","המיניונים","הנוסע השמיני","לוליטה","הדרדסים","בארט סימפסון","מחוברים"),
            R.id.companies to listOf("פאלפל כדורי","הבית של חומוס","מקורות","זארה","האו","טויוטה","מכון","ויצמן","גוגל","מעריב","איקאה","אמזון","יוטיוב","נייק","אאודי","וויז","וואטסאפ","גלגל'צ","פולסווגן","מיקרוסופט","סטימצקי","פולסווגן","רמי לוי","קינלי","אלדן ","נטפליקס","צהל","סטארבקס","האוניברסיטה העיברית","שילב"," נוקיה","סקייפ","הייניקן","פפסי","הטאבון בון בון על האש"),
            R.id.objects to listOf("בגרות","רוח הקודש","ארץ עיר","האנגאובר","טלפתיה","סיירת מטכ'ל","צלילה","גילוי אמריקה","גובלין","ווי פיי","סמוראי","מחשב נייד","כרטיס אשראי","שרירי בטן","ריקוד סלסה","מבוך","פומפיה","דינאמיט","לייק","באג אלפיים","קלסטרופוביה","בום על-קולי","המונה ליזה","טבעת נישואים","תואר ראשון","הרנסאנס","החומה הסינית","כוח המשיכה","פאי"),
            R.id.culture_leaders to listOf("יו 2","ארז טל","הריסון פורד","ג'ון לנון","מרלין מונרו","סטינג","שרית חדד","אלביס פרסלי","סנדרה בולוק","נטלי פורמן","ביטלס","ספייס גירלס","עמוס עוז","מדונה","ג'וליה רוברטס","פבלו פיקאסו","ג'ון לנון"," משינה ","מטאליקה ","דיוויד בואי","וויטני יוסטון","נירוונה","ג'סטין טימברלייק","טום האנקס ","אופרה ווינפרי"),
            R.id.sports to listOf("טריאתלון","שאקיל אוניל","מכבי תל אביב","הוקי","ערן זהבי","קפיצה לרוחק","קארים עבדול ג'באר","ניב ריסקין","ריאל מדריד","יוסי אבוקסיס","יוסיין בולט","אבי נימני","אריק זאבי","פורמולה-1","ניו-יורק ניקס","חיים רביבו","טל ברודי","מארדונה","לברון ג'יימס","קריקט","הטלת קידון","הדיפת כדור ברזל","קובי בריאנט","רפאל נדל","מייקל שומאכר","מוחמד עלי"),
            R.id.nature to listOf("המזרח הרחוק","צונמי","דלעת","דורבן","שושנת ים","נדל","איגואנה","חסרי חוליות","פינצ'ר","קיפוד ים","סרטן","לוויתן","נענע","בבון","ממותה","זברה","גויאבה","פלפל חריף","פפריקה","טיגריס","נקר","כלניות","קפה","גבעול","קשיו","צב","כינים","זאב","צנון","ציפור גן-עדן","פרוקי רגליים","אלמוג","דוכיפת","צ'יוואווה","אגוז","שפן","אפרסק"),
            R.id.scientists to listOf("דלאי לאמה","דונלד טראמפ","המלכה אליזבת","סטיב ג'ובס","הילרי קלינטון","ארכימדס","ג'וסף סטאלין","אפלטון","וולדימיר פוטין","הנרי פורד","רנה דקארט","לואי פסטר","מאו דזה דונג","מרטין לות'ר קינג","אריסטו","תות ענח אמון","נפולאון בונפרטה","גלילאו גליליי","אמא תרזה","חוסני מוברק","אברהם לינקולן","ישו","ג'וסף סטאלין","מארק צוקרברג","אברהם אבינו","ניל אמסטרונג","יוליוס קיסר","ג'ון קנדי","בניימין זאב הרצל"),
        )

    init {
        val numberOfCategories = filteredCategories.count()
        val wordsPerCategory = IntArray(numberOfCategories)

        for (i in 0 until numberOfCategories) {
            wordsPerCategory[i] = (1..numberOfWords).random()
        }

        val sum = wordsPerCategory.sum()
        val scale = numberOfWords.toDouble() / sum.toDouble()

        for ((index, number) in wordsPerCategory.withIndex()) {
            wordsPerCategory[index] = (number * scale).toInt()
        }

        while (wordsPerCategory.sum() != numberOfWords) {
            wordsPerCategory[(0 until numberOfCategories).random()] += 1
        }
        val wordsArray = Array(numberOfWords) { "" }
        var i = 0
        for ((index, category) in filteredCategories.withIndex()) {
            val words = wordBank[category]!!.shuffled().take(wordsPerCategory[index])
            for (j in 0 until wordsPerCategory[index]) {
                wordsArray[i] = words[j]
                i++
            }
        }
        wordsArray.shuffle()
        this.wordsArray = wordsArray.toSet()

        currentTeam = if ((1..2).random() == 1) team1 else team2
    }

    fun startRound() {
        roundWordList = wordsArray.shuffled().toMutableList()
        guessedRight.removeAll { true }
        pointer = 0
    }

    fun drawPetek(): String {
        return roundWordList[pointer]
    }

    fun wrongGuess() {
        pointer = ++pointer % roundWordList.size
        currentTeam.wrongGuess()
    }

    fun teamSwitch() {
        currentTeam = if (currentTeam === team1) team2 else team1
        roundWordList.shuffle()
        pointer = 0
    }

    fun rightGuess() {
        guessedRight.add(roundWordList[pointer])
        roundWordList.removeAt(pointer)
        try {
            pointer %= roundWordList.size
        } catch (e: ArithmeticException) {
            pointer = 0
        }
        currentTeam.rightGuess()
    }
}

fun main() {

    val t1 = Team(1)
    val t2 = Team(2)
    val c = Ptakim(
        arrayOf(R.id.places, R.id.objects, R.id.scientists).toIntArray(),
        6, t1, t2
    )
    println(c.wordsArray.joinToString(", "))
    var curTeam = t1
    c.startRound()
    while (!c.noMoreCards) {
        println(c.drawPetek())
        when (readln().toInt()) {
            0 -> c.wrongGuess()
            1 -> c.rightGuess()
            2 -> {
                c.teamSwitch()
                curTeam = if (curTeam === t1) t2 else t1
            }
            else -> break
        }

    }
}