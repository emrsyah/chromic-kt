package com.example.pwpb

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
//import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

//import
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_layout_utama.*
import kotlinx.android.synthetic.main.item_hero.view.*
import java.util.*
import kotlin.collections.ArrayList


data class Hero(
    val name: String,
    val desc: String,
    val image: String,
    val id: String,
    val origin: String,
)

class HeroAdapter(private val heroes: MutableList<Hero>) : RecyclerView.Adapter<HeroAdapter.HeroHolder>(), Filterable {


    var heroFilterList = ArrayList<Hero>()

    init{
        heroFilterList = heroes as ArrayList<Hero>
    }

    fun getListItem() {
        Log.d("search" , heroFilterList.toString())
    }

    override fun getFilter() : Filter{
        return object: Filter(){
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val charSearch = p0.toString()
//                Log.d("search 1", charSearch)
                if (charSearch.isEmpty()) {
                    heroFilterList = ArrayList(heroes)
                } else {
                    val resultList = ArrayList<Hero>()
                    for (row in heroFilterList) {
//                        Log.d("search 2", row.name.lowercase(Locale.ROOT))
                        if (row.name.lowercase(Locale.ROOT)
                                .contains(charSearch.lowercase(Locale.ROOT))
                        ) {
                            Log.d("search av", row.name)
                            resultList.add(row)
                        }
                    }
                    heroFilterList = resultList
                }
                val filterResults = FilterResults()
//                Log.d("search 3", heroFilterList.toString())
                filterResults.values = heroFilterList
                return filterResults
            }

            @Suppress("UNCHECKED CAST")
            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                heroFilterList = p1?.values as ArrayList<Hero>
//                Log.d("search 4", heroFilterList.toString())
                notifyDataSetChanged()
            }
        }
    }

    fun filterByChip(origin: String, query: String) {
        heroFilterList = ArrayList(heroes)
        if(origin != "Semua"){
            val resultList = ArrayList<Hero>()
            for (row in heroFilterList) {
                if (row.origin.lowercase(Locale.ROOT) == origin.lowercase(Locale.ROOT))
                {
                    resultList.add(row)
                }
            }
            heroFilterList = resultList
        }
        notifyDataSetChanged()
        if(query.length > 0) {
            filter.filter(query)
        }
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): HeroHolder {
        return HeroHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_hero, viewGroup, false))
    }

    override fun getItemCount(): Int = heroFilterList.size

    override fun onBindViewHolder(holder: HeroHolder, position: Int) {
        holder.bindHero(heroFilterList[position], holder)
    }

    inner class HeroHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvHeroName = view.txtHeroName
        private val imgHero = view.imgHeroes
        private val desc = view.txtDesc
        private val origin = view.origin

        @SuppressLint("UseCompatLoadingForColorStateLists")
        fun bindHero(hero: Hero, holder: HeroHolder) {
            val descKu = hero.desc
            lateinit var descSub: String
            if(descKu.length >= 40) {
                descSub = descKu.subSequence(0, 40).toString()
            } else{
                descSub = descKu
            }
            val descSub2 = ("$descSub...")
            tvHeroName.text = hero.name
            desc.text = descSub2
            tvHeroName.tag = hero.id
            desc.tag = descKu
            origin.text = hero.origin
            if(hero.origin == "DC"){
                origin.backgroundTintList = holder.itemView.context.resources.getColorStateList(R.color.pallete);
            } else{
                origin.backgroundTintList = holder.itemView.context.resources.getColorStateList(R.color.redcolor);
            }
            imgHero.tag = hero.image
            Picasso.get().load(hero.image).into(imgHero)
        }

        private fun setOriginBg(origin: String){
        }

    }

    override fun onBindViewHolder(holder: HeroHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)
        holder.itemView.setOnClickListener{
//            Toast.makeText( holder.itemView.context, "Halo Kuy", Toast.LENGTH_LONG).show()
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            val txtName: TextView = holder.itemView.findViewById(R.id.txtHeroName)
            val txtDesc: TextView = holder.itemView.findViewById(R.id.txtDesc)
            val imageV: ImageView = holder.itemView.findViewById(R.id.imgHeroes)
            val originV : AppCompatButton = holder.itemView.findViewById(R.id.origin)
            val name = txtName.text
            val desc = txtDesc.tag
            val image = imageV.tag
            val id = txtName.tag
            val origin = originV.text
            Log.d("nama", name.toString())
            Log.d("desc", desc.toString())
            Log.d("image", image.toString())
//            intent.putExtra("image_url", holder.itemView.findViewById<ImageView>(R.id.imgHeroes).toString())
            intent.putExtra("image_url", image.toString())
            intent.putExtra("name", name.toString())
            intent.putExtra("desc", desc.toString())
            intent.putExtra("id", id.toString())
            intent.putExtra("origin", origin.toString())
            Log.d("activity", "Aktifitas tertekan")
//            holder.itemView.context.startActivity(intent)
            holder.itemView.context.startActivity(intent)
        }
    }
}

//fun getData(v: RecyclerView, l: AppCompatActivity): MutableList<Hero>{
// Log.d("Fetch", "Getting Data Started")
//    val db = Firebase.firestore
//    val list = mutableListOf<Hero>()
//    db.collection("characters")
//        .get()
//        .addOnSuccessListener { result ->
//            for (document in result) {
//                Log.d("Fetch", "${document.id} => ${document.data}")
//                val name = document.data["name"]
//                val desc = document.data["desc"]
//                val url = document.data["image_url"]
//                list.add(Hero(name = name.toString(), desc = desc.toString(), image = url.toString()))
////                all.add(Hero(name = name.toString(), desc = desc.toString(), image = url.toString()))
//            }
//            val heroesAdapter = HeroAdapter(list)
//            v.apply {
//                layoutManager = LinearLayoutManager(l)
//                adapter = heroesAdapter
//            }
////            val rView : RecyclerView = findViewById(R.id.rView)
////            return list
//        }
//        .addOnFailureListener { exception ->
//            Log.d("Fetch", "Error getting documents: ", exception)
//        }
//        .addOnCompleteListener{
//            Log.d("Fetch", list.size.toString())
//        }
//
//            return  list
//}

interface MyCallback {
    fun onCallback(value: MutableList<Hero>)
}

//fun getDataRealtime(v: RecyclerView, l: AppCompatActivity, lists: MutableList<Hero>): MutableList<Hero>{
//    Log.d("Fetch", "Getting Data Started")
//    val db = Firebase.firestore
//    val list = mutableListOf<Hero>()
//    db.collection("characters")
//        .addSnapshotListener { value, e ->
//            if (e != null) {
//                Log.w("fetch", "Listen failed.", e)
//                return@addSnapshotListener
//            }
//
//            for (doc in value!!) {
//                val name = doc.getString("name")
//                val desc = doc.getString("desc")
//                val url = doc.getString("image_url")
//                lists.add(Hero(name = name.toString(), desc = desc.toString(), image = url.toString()))
//                Log.d("fetch", "Current cites in CA: ${doc.getString("name") + doc.getString("image_url") + doc.getString("desc")?.subSequence(0,20)}")
//            }
//            val heroesAdapter = HeroAdapter(lists)
////            heroesAdapter.
//            v.apply {
//                layoutManager = LinearLayoutManager(l)
//                adapter = heroesAdapter
//            }
////            Log.d("fetch", "Current cites in CA: $list")
//        }
//    return list;
//}

fun getDataRealtimeWithCallback(myCallback: MyCallback){
    Log.d("Fetch", "Getting Data Started")
    val db = Firebase.firestore
    db.collection("characters")
        .addSnapshotListener { value, e ->
            val list = mutableListOf<Hero>()
            if (e != null) {
                Log.w("fetch", "Listen failed.", e)
                return@addSnapshotListener
            }

            for (doc in value!!) {
                val name = doc.getString("name")
                val desc = doc.getString("desc")
                val url = doc.getString("image_url")
                val id = doc.id
                val origin = doc.getString("origin")
                list.add(Hero(name = name.toString(), desc = desc.toString(), image = url.toString(), id = id.toString(), origin = origin.toString()))
            }
//            Log.d("fetch 2", list.toString())
            myCallback.onCallback(list)
//            heroesAdapter.
//            Log.d("fetch", "Current cites in CA: $list")
        }
}

fun addProfileImage(url: String, profile: ImageView){
//    https://avatars.dicebear.com/api/adventurer-neutral/your-custom-seed.svg
//    profile.tag = url
    Picasso.get().load(url).into(profile)
//    Picasso.get().load("https://avatars.dicebear.com/api/adventurer-neutral/your-custom-seed.svg").into(profile)
}


class layout_utama : AppCompatActivity() {
    lateinit var myAdapter: HeroAdapter
    private lateinit var auth: FirebaseAuth
    var list = mutableListOf<Hero>()
    //    lateinit var list: MutableList<Hero>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout_utama)
        val rView : RecyclerView = findViewById(R.id.rView)
        val layoutU = this@layout_utama
        auth = Firebase.auth
        val profile: ImageView = findViewById(R.id.profileKu)
        val userEmail = auth.currentUser?.email
        val url = "https://avatars.dicebear.com/api/adventurer-neutral/$userEmail.jpg"
        addProfileImage(url, profile)
//        val lt = getDataRealtime(rView, layoutU, list)
        getDataRealtimeWithCallback(object: MyCallback{
            override fun onCallback(value: MutableList<Hero>)  {
                        Log.d("fetch akhir", value.toString())
                myAdapter = HeroAdapter(value)
                rView.apply {
                    layoutManager = LinearLayoutManager(layoutU)
                    adapter = myAdapter
                }
            }
        })

        val myDesc = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla a mattis nulla, a venenatis dolor. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Aliquam sed tellus eros. Aenean lobortis elit nec lacus cursus ultricies. Nulla eu finibus diam. Pellentesque sed tempor felis, at rhoncus orci. Suspendisse id nisl vitae tortor fermentum volutpat et sit amet ligula. Sed venenatis neque sed lectus blandit convallis. Sed vehicula volutpat ex nec elementum. Etiam eleifend tincidunt dolor a dignissim. Vestibulum eu semper sem, eu volutpat velit. Pellentesque ullamcorper magna vitae justo viverra vulputate. Suspendisse eu molestie nibh."

//        myAdapter.filterByChip("Marvel")

        searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val choice = chipGroup.findViewById<Chip>(chipGroup.checkedChipId).text
                myAdapter.filterByChip(choice.toString(), newText.toString())
//                myAdapter.filter.filter(newText)
//                myAdapter.getListItem()
//                Log.d("filter", choice.toString())
//                Log.d("search", adapter.getListItem().toString())
//                adapter.getListItem()
                return false
            }

        })

        val bottomSheetFragment = BottomSheetFragment()
        fab.setOnClickListener {
            bottomSheetFragment.show(supportFragmentManager, "BottomSheetDialog")
        }

        profile.setOnClickListener {
            Log.d("intent", url + userEmail)
            val intent = Intent(this, Profiles::class.java)
            intent.putExtra("image_url", url)
            intent.putExtra("email", userEmail)
            startActivity(intent)
        }

        chipGroup.setOnCheckedStateChangeListener{ chipGroup, checkedId ->
            val choice = chipGroup.findViewById<Chip>(checkedId[0])?.text
            val input = searchBar.query.toString()
//            Log.d("filter", input)
            myAdapter.filterByChip(choice.toString(), input)
        }

    }
}