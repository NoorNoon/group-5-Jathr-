package com.example.jather.data

/**
 * The one list of demo clients for the whole app.
 * Every screen (plans, specialist clients, home, account, appointments, payment)
 * reads names from here, so the same person has the same id and name everywhere.
 * Replace with real users from Firebase later.
 */
data class DemoClient(
    val id: Int,
    val name: String,
    val goal: String,
    val week: Int,
    val status: String
)

object DemoData {

    val clients = listOf(
        DemoClient(1, "لارا الشمراني", "إنقاص الوزن", 1, "نشط"),
        DemoClient(2, "نورة القحطاني", "تثبيت الوزن", 2, "نشط"),
        DemoClient(3, "هند الشهري", "إنقاص الوزن", 4, "نشط"),
        DemoClient(4, "سارة العنزي", "زيادة الوزن", 6, "يحتاج متابعة"),
        DemoClient(5, "نور السعود", "تثبيت الوزن", 2, "يحتاج متابعة"),
        DemoClient(6, "فهد السبيعي", "نمط حياة صحي", 12, "مكتمل")
    )

    /** The specialist shown to clients (home, notes). */
    const val SPECIALIST_NAME = "أ. سارة الشمري"

    /** The client who is "logged in" when the role chosen at login is client. */
    const val CURRENT_CLIENT_ID = 2

    fun client(id: Int): DemoClient? = clients.find { it.id == id }

    fun clientName(id: Int): String = client(id)?.name ?: ""

    val currentClientName: String
        get() = clientName(CURRENT_CLIENT_ID)
}
