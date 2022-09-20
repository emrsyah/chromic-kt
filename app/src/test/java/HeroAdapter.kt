import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pwpb.Hero
import com.example.pwpb.R


class HeroAdapter(private val heroes: List<Hero>) : RecyclerView.Adapter<HeroAdapter.HeroHolder>() {

        override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): HeroHolder {
            return HeroHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_hero, viewGroup, false))
        }

        override fun getItemCount(): Int = heroes.size

        override fun onBindViewHolder(holder: HeroHolder, position: Int) {
            holder.bindHero(heroes[position])
        }

        inner class HeroHolder(view: View) : RecyclerView.ViewHolder(view) {
            private val tvHeroName = view.txtHeroName


            fun bindHero(hero: Hero) {
                tvHeroName.text = hero.name
            }
        }
    }


