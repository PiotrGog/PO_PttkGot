from sqlalchemy import create_engine
from sqlalchemy import inspect
from sqlalchemy.orm import load_only
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.ext.automap import automap_base
from sqlalchemy.orm import Session
from sqlalchemy.orm import defer
from random import randint
import ast
import random

Base = automap_base()
engine = create_engine('mysql://root:@127.0.0.1:3306/PTTK_GOT')
connection = engine.connect()


Base.prepare(engine, reflect=True)

Character_Specialization = Base.classes.Character_Specialization
Race_Trump = Base.classes.Race_Trump
Race_Class = Base.classes.Race_Class


# # FILL THE TEAMS TABLE
with open('teams_names.txt') as f:
    TEAM_NAMES = f.read().splitlines()

session = Session(engine)

teams = []
for team in TEAM_NAMES:
    teams.append(Teams(TeamName=team))

session.add_all(teams)
session.commit()
team_ids = [v[0] for v in session.query(Teams.IdTeam).all()]

# # FILL THE RACES TABLE
with open('races.txt', encoding='latin-1') as f:
    RACES_NAME_DESCR = f.read().splitlines()

races = []
for race in RACES_NAME_DESCR:
    # print(race)
    race = race.split(';')
    races.append(Races(RaceName=race[0], RaceDescription=race[1]))

session.add_all(races)
session.commit()

races_ids = [v[0] for v in session.query(Races.IdRace).all()]


# # FILL THE SPECIALIZATION TABLE
with open('classes.txt', encoding='latin-1') as f:
    SCECIALIZATIONS = f.read().splitlines()

specs = []
for spec in SCECIALIZATIONS:
    # print(spec)
    spec = spec.split(':')
    specs.append(Specializations(SpecName=spec[0], SpecDescription=spec[1]))

session.add_all(specs)
session.commit()


# # FILL THE CLASSES TABLE
with open('basic_classes.txt', encoding='latin-1') as f:
    CLASSES = f.read().splitlines()


classes = []
for clas in CLASSES:
    # print(clas)
    clas = clas.split(':')
    classes.append(Classes(ClassName=clas[0], ClassDescription=clas[1]))

session.add_all(classes)
session.commit()


# # FILL THE ACTIONS TABLE
ACTIONS = [("Attack", "Attack another object"), ("Block", "Block some attack"), ("Throw spell", "Throw spell from your magic book"), ("Move forward", "Do one step forward"), ("Move backward", "Do one step backward"), ("Move left", "Do one step left"), ("Move right", "Do one step right"), ("Move left front", "Do one step diagonally to left front"), ("Move left back", "Do one step diagonally to left back"), ("Move right front", "Do one step diagonally to right front"), ("Move right back", "Do ne step diagonally to right back"), ("Talk", "Talk to some champion"), ("Bait", "Provoke some champion"), ("pick up", "Pick up some item"), ("Throw up", "Throw up some item"), ("Accept quest", "Accept some quest from principal"), ("Sleep", "Sleep for some time"), ("Fight", "Start fight with some other player"), ("Jump", "Just jump"),
           ("Sit", "Sit at some object prepared to do id"), ("Dig", "Dig to find some brecleaus"), ("Bow shot", "Shot from a bow"), ("Crossbow shot", "Shot from a crossbow"), ("Wear", "Wear some item"), ("Get reward", "Get reward for some quest"), ("Get on ship", "Just get on ship"), ("Get on horse", "Just get on horse"), ("Get on dragon", "Just get on some dragon"), ("Some cigarette", "Relax"), ("Eat", "Eat something"), ("Drink", "Drink something"), ("Take elixir", "Drink some elixir"), ("Buff", "Buff someon or someone else"), ("Learn spell", "Learn some spell"), ("Learn skill", "Learn some skill"), ("Get a level", "Get a new level"), ("Complete quest", "Complete some quest"), ("Read", "Read book or scroll"), ("Write", "Write on book or on scroll"), ("Send", "Send message to someon"), ("Call horse", "Order your horse to come to you")]

actions = []

for act in ACTIONS:
    # print(act)
    actions.append(Actions(ActionName=act[0], ActionDescription=act[1]))
session.add_all(actions)
session.commit()

actions_ids = [v[0] for v in session.query(Actions.IdAction).all()]

# # FILL THE BUFFS TABLE
BUFFS = [("Attack upgrage", "Upgrade your attack skill"), ("Def upgrade", "Upgrade your defence skill"), ("Dexterity upgrade", "Upgrade your dexterity skill"), ("Power upgrade", "Upgrade your magic power"), ("Knowledge upgrade", "Upgrade your knowledge"), ("Health upgrade", "Upgrade your maximum level of health"), ("Thief skills upgrade", "Upgrade your thief skills"), ("Mana upgrade", "Upgrade your maximum level of mana"), ("Right hand upgrade", "Upgrade one-handed weapon skills"), ("Left hand upgrade", "Upgrade shield using skills"), ("Strong muscles", "Two-handed weapon upgrade"), ("Heavy armor upgrade", "Upgrade statistics of heavy armor"), ("Med armor upgrade", "Upgrade statistics of medium armor"), ("Light armor upgrade", "Upgrade statistics of light armor"), ("Eagle eye", "Upgrade your accurity"),
         ("Long seeing", "You can see further"), ("7-miles shoues", "You can go thru 2 fields in one move"), ("Troll regeneration", "Your health regenerates 2x faster"), ("Mana regeneration", "Your mana regenerates 2x faster"), ("Usain Bolt", "Your energy regenerates 2x faster"), ("Good taste", "Food and drinks work faster"), ("Toxic blood", "Elixirs work faster"), ("Strong head", "Alkohol doesn't work"), ("Rock skin", "Arrows mean nothing to you"), ("Magic shield", "Magical damage reduced to 50%"), ("Hard skin", "Physical damages reduced to 50%"), ("Golem", "Physical damages reduced to 10%"), ("Quick hands", "You can do 2 attacks in one move"), ("Better helmets", "Helmets work better"), ("Better boots", "Boots work better"), ("Better gloves", "Gloves work better"), ("Better trousers", "Trousers work better")]
buffs = []

for buff in BUFFS:
    # print(buff)
    buffs.append(Buffs(BuffName=buff[0], BuffDescription=buff[1]))
session.add_all(buffs)
session.commit()

buffs_ids = [v[0] for v in session.query(Buffs.IdBuff).all()]

# FILL THE MODIFERS TABLE
MODIFIRES = ["Attack modifier", "Def modifier", "Dexterity modifier", "Energy modifier", "Mana modifier", "Health modifier", "Accuraty modifier", "Knowledge modifier", "Power modifier", "Thief modifier", "Strong modifier", "Health regen modifier", "Mana regen modifier", "Toxic modifier", "Eagle eye modifier", "Magical shield modifier", "Head modifier", "Helmet modifier", "Gloves modifier", "Boots modifier", "Trousers modifier",
             "Hard skin modifier", "Rock skin modifier", "Elixir modifier", "Spell modifier", "Bow modifier", "Crossbow modifier", "Left hand modifier", "Right hand modifier", "Lucky modifier", "Wisdom modifier", "Muscles modifier", "Two-handed weapon modifier", "Fire spells modifier", "Water spells modifier", "Earth spells modifier", "Air spells modifier", "Mind spells modifier", "Utility spells modifier", "Call spells modifier", "Move modifier", ]
mods = []
for mod in MODIFIRES:
    # print(mod)
    mods.append(Modifiers(ModName=mod))
session.add_all(mods)
session.commit()

mod_ids = [v[0] for v in session.query(Modifiers.IdMod).all()]

# FILL THE ZONES TABLE
ZONES = ["Left Foot", "Right foot", "Left leg", "Right leg", "Belt", "Left hand", "Right hand", "Left forearm", "Right forearm",
         "Left arm", "Right arm", "Trunk", "Neck", "Left ear", "Right ear", "Left finger", "Right finger", "Tail", "Eyes"]
zones = []
for zon in ZONES:
    # print(zon)
    zones.append(Zones(ZoneName=zon))
session.add_all(zones)
session.commit()


# FILL THE FIELDS TABLE
fields = []
for x in range(10000):
    fields.append(Fields(XCoordinate=randint(
        1, 10000), YCoordinate=randint(1, 10000)))

session.add_all(fields)
session.commit()

fields_ids = [v[0] for v in session.query(Fields.IdField).all()]

# FILL THE BUFFS TABLE
neibors = []
for x in range(10000):
    f1 = random.choice(fields_ids)
    f2 = random.choice(fields_ids)
    while(f1 == f2):
        f1 = random.choice(fields_ids)
    neibors.append(Board(Field_1=f1, Field_2=f2))
session.add_all(neibors)
session.commit()


#  FILL THE CHEMIPONS TABLE
NAMES_CHAR = ["Magic Mark", "John", "Lucky Patric", "Mark", "Johann", "Joanna", "Anna", "Anabel", "Aureli", "Harry the Brave", "Henry", "Sandro", "Nimbus", "Orrin", "Cedric", "Valeska", "Edric", "Eric", "Lort Haart", "Christian", "Sorsha", "Sir Lochan", "Fiona", "Rakesh", "Marius", "Darius", "Ignatio", "Octavia", "Otton", "Sir Ignatius", "Kyrre", "Pyre", "Aiden", "Axis", "Calid", "Bozydar", "Kovalsky", "Radioactive Rico", "Reginald", "Romualda", "Ashe", "Fiora", "Dacotta", "Ves", "Vei", "Quenn", "Nami", "Xarfex", "Pasis", "Pendradon", "Pandrodor", "Pandora", "Ignissia", "Morene", "Khardimon", "Saturas", "Milten", "Lestek", "Corn", "Diego", "Gonzales", "Fiur", "Paris", "Kalt", "Kari", "Clarie", "Sir Christian", "Gelu", "Clance", "Kendal", "Katarina", "Ari", "Amy", "Draco", "Gen", "Yog", "Xeron", "Phineas", "Mutare", "Rexuan", "King Rex", "Ordwald", "Kirgor", "Boragus", "Adrianne", "Gabriel", "Deux", "Ex", "Uomo de Machineri", "Bomber", "Jack", "Born", "Bron", "Drakon", "Wystan", "Balnazar", "Baltazar", "Barnaba",
              "Bari", "Barx", "Barxus", "Barxuan", "Alkin", "Korbac", "Gerwulf", "Werewolf", "Wolfan", "Broghild", "Brunhilda", "Miranda", "Voy", "Vox", "Rosic", "Veridsh", "Merist", "Styx", "Styg", "Andra", "Tina", "Martino", "Rico Marino", "Konrado", "Maroco", "Straker", "Stalker", "Vesemir", "Vokial", "Charna", "Triss", "Yen", "Isra", "Istan", "Clavius", "Galthran", "Septima", "Septiema", "Secundo", "Tertia", "Quardia", "Quinto", "Octavius", "Nimus", "Thant", "Xsi", "Vidomina", "San Escobar", "Nag", "Nagash", "Loretta", "Lorelei", "Arlach", "Dace", "Dunne", "Ajit", "Damacon", "Anaconda", "Gunnar", "Synca", "Shaktia", "Shakti", "Almanar", "Alamar", "Almar", "Jegear", "Jager", "Jaguar", "Malakitch", "Jadeiete", "Peon", "Artas", "Sephiront", "Deemer", "Darkstorn", "Gurinsohn", "Jarkabes", "Shiva", "Gretchin", "Krellon", "Kreon", "Krelion", "Crag Hack", "Tyraxor", "Gird", "Vey", "Dessa", "Rosh", "Terek", "Zubin", "Zug", "Rug", "Rab", "Gundulla", "Oris", "Axiss", "Saurg", "Smaug", "Smogg", "Cedricia", "Barry", "Misterio"]
characters = []


for x in range(10000):
    characters.append(Characters(Name=random.choice(NAMES_CHAR)+str(x),  # +  str(randint(1,2939945)),
                                 Level=randint(1, 99),
                                 Strength=randint(0, 30),
                                 Ability=randint(0, 30),
                                 Construction=randint(0, 30),
                                 Intellect=randint(0, 30),
                                 Prudence=randint(0, 30),
                                 Charisma=randint(0, 30),
                                 IdField=random.choice(fields_ids),
                                 IdRace=random.choice(races_ids),
                                 IdTeam=random.choice(team_ids)))
session.add_all(characters)
session.commit()
characters_ids = [v[0] for v in session.query(Characters.IdCharacter).all()]


QUEST_DESCRIPTION = ["Please excuse me, champion. I need a favor of you. Fighting those filthy vermin who dare challenge our empire is both a great honor and a pleasure. However, it seems they have grown in strength lately thanks to some new siege weapon. Hero, seek out that weapon and destroy it. Kill any who stand in your way, we'll teach a lesson or two to those vicious brutes. I offer you my assistance, but it's up to you if you want me to join you or not. I don't think you'll have much troubles dealing with those brutes. Kill as many of them as you'd like, just make sure you don't forget your main goal. I had hoped I'd be able to reward you handsomely, but unfortunately I don't have a lot left. Be swift and strike true hero, we're counting on you.", "Please, hero. I'm in need of your assistance.  I've been betrayed, betrayed by my own foolishness. I knew I shouldn't have hired mercenaries, instead of fighting the enemy they joined them. Hero, teach them a lesson in honor and make sure they remember it. Curse those nasty creatures. If you think you'll need any help, I'm willing to come along. Hero, act swiftly when dealing with the creatures. I'm not asking you to kill all of them, just make sure you do what I ask of you. Should you succeed I will be able to repay you handsomely, it'll be worth your troubles. You best get going, there's no time to waste. We're counting on you.", "Pardon me, champion. Would you lend me your aid.  The other day I caught a huge fish. It was enormous! And by enormous I mean it was the biggest fish I've ever seen, it was probably the biggest fish anybody has ever and will ever see. Unfortunately, after I managed to catch it, the fish got away. It took my fishing rod with it and I really need it back. I'd go and get it myself, but it's underwater and I'm pretty sure that fish has a vendetta against me now. Would you mind getting it for me? Unfortunately there's not much I have to reward you with, but I think there's a few bits and pieces laying around.", "Forgive me, traveler. I could really use a helping hand.  I need to do more research, a lot more research and for that I need my books. All of my books are neatly organized in my library, but my legs aren't what they used to be, I can no longer climb the ladders to reach the upper shelves. Please, would you be so kind and fetch me the books I need. My research is very important after all. I'll be able to reward you decently, it's nothing fancy, but it'll be worth your while.", "Scuse me, hero. Please, lend me your hand. A recent outbreak of some weird disease has caused the townsfolk to go insane. Unfortunately we have no cure and their disease will lead to a painful death. Hero, I know this is a terrible task to ask of you, but we need you to end their suffering. Know that we found who's responsible, we'll get those abominable barbarians. If you think you'll need any help, I'm willing to come along. Be strong and swift when fighting the barbarians. Try to take down as many of them as possible, the less of a threat they pose the better. I wish I had something better to reward you with, but what I have ain't too bad either. For justice and honor!", "Forgive me, champion. I need you to intervene. Seven years it has been, seven years since we secured our lands and we could live in peace. But now they're back and they're stronger than ever. Hero, we'll need your help to get rid of those vile brutes. It'll be dangerous, but I'm willing to come along if you need me to.I don't think you'll have much troubles dealing with those brutes. Make sure they regret the day they ever set foot in our lives. I had hoped I'd be able to reward you handsomely, but unfortunately I don't have a lot left. Safe journey, hero. We believe in you.", "Excuse me, traveler. Help out a person in need. The time to strike is now, but we're low on manpower, so I need you to join us. If we don't strike now, we'll never get rid of them. Join us and fight those terrible barbarians. I'd love to join you, but it'll be up to you. I don't want to get in the way. Tread carefully hero, only a fool would underestimate the barbarians. Killing them all won't be needed of course, I'm sure your skills will do enough damage to them. I'm afraid there's not much left I can reward you with, but I think I can still make it worth your troubles. Please succeed hero, we believe in you.", "Excuse me, adventurer. Spare me some of your time.We're on the offensive at last, but the battle has not been won just yet. To win we will have to go all out and we can use every fighter we can get. Hero, join us and help us kill those wretched trolls. I won't be able to join you, but I have full confidence in you. I know you'll succeed.We're counting on you to deal with those trolls. You don't have to kill them all, just make sure they'll never return.I hope you're not looking for a big reward, but we'll still be able to reward you with somethind decent. Make haste hero, but tread carefully.", "Please excuse me, champion. Help out a person in need. I love the feeling of moss beneath my feet, it's probably the greatest feeling ever. Now, I've come up with a plan which will cover the floors of my house in moss, so I can always experience the greatest feeling ever. The best moss grows in darkwood forest to the South, but I'm too scared to go in there and get the moss myself. It's so dark and creepy in there. Could you gather the moss for me?I had hoped I'd be able to reward you handsomely, but unfortunately I don't have a lot left.", "You there, adventurer. You're a sight for sore eyes.A storm is coming and it's no ordinary storm. A war is upon is, but we're not ready yet. This is where you come into our plan. We need you to take a small party and sabotage the incoming army, that'll give us enough time to deal with those worthless miscreants. I had hoped I could you join, but unfortunately I cannot. But I doubt you'd need my help anyway.You are fully capable to handle those miscreants. If you can, kill as many of them as possible. We want to live in peace and safety.I have little possessions left, but I'll reward you with what I have. Be swift and strike true hero, we're counting on you.", "Pardon me, champion. Please, lend me your hand. As you can see these lands have been corrupted. For the longest time we didn't know what caused it, but now we do. It's the vile magic used by those wicked beings at the foot of the mountains. Hero, please go there and get rid of them before they taint everything, get rid of those disgusting aliens. I'll join you, if you'll have me. It'll be my pleasure. Hero, act swiftly when dealing with the aliens. Killing them all won't be needed of course, I'm sure your skills will do enough damage to them. Unfortunately there's not much I have to reward you with, but I think there's a few bits and pieces left. Please succeed hero, we believe in you.",
                     "Excuse me, hero. Help out a person in need. My daughter has fallen sick and she has to stay in bed for a few more days, but she really wants to go out and play. So I thought, why not bring the outside to her. Unfortunately I cannot leave her, she needs me to be here. Would you mind collecting some objects from outside? She really looves the beach and the forest, so a great big seashell and some colorful flowers are sure to cheer her up.I will be able to reward you greatly, if you decide to help me of course.", "Please, champion. I need your help.Seven years it has been, seven years since we secured our lands and we could live in peace. But now they're back and they're stronger than ever. Hero, we'll need your help to get rid of those vicious humans. If you think you'll need any help, I'm willing to come along.I don't think you'll have much troubles dealing with those humans. Try to take down as many of them as possible, the less of a threat they pose the better.Fortunately I have enough left to reward you greatly for the efforts you have to go through. Best of luck hero, make sure you return in one piece.", "Pleease excuse me, adventurer. I need you to save us.Eye for an eye! Blood for blood! If they wish to play dirty, so will we. Hero, you've seen what they've done to our lands, it's time to unleash our vengeance upon those horrible lowlives. I wish I could join you, but alas I cannot. Know that I've put all my faith in you, you will succeed. I know it.Be strong and swift when fighting the lowlives. Make sure they regret the day they ever set foot in our lives. Unfortunately there's not much I have to reward you with, but I think there's a few bits and pieces left. For justice and honor!", "Scuse me, champiion. Please, help me out.I craft jewelry for a living, but I'm running out of jewels. My old supplier hasn't returned in months and I have no idea what happened to him, but I really need more gems. I heard there's quite a lot of them in the abandoned mines to the North, but I cannot leave my shop, what if a customer comes along? Would you be so kind and gather me some gems, I doubt that abandoned mine is dangerous, it's just a mine.I'm happy to say I'll be able to reward you handsomely for your troubles.", "Please excuse me, hero. Please, lend me your hand.They've burned down half our village with wicked sorcery. Hero, I don't know what foul magic they wield, but we cannot let them go unpunished. Please unleash justice upon those cruel orcs. I'll be coming along with you, at least, as long as you don't mind.You are fully capable to handle those orcs. Kill as many of them as you'd like, just make sure you don't forget your main goal.I will be able to reward you greatly, should you return successfully. You best get going, there's no time to waste. We're counting on you.", "Please, adventurer. I need a favor of you.A storm has destroyed part of our museum, a terrible tragedy. All of those relics, covered in rubble once again or worse, they might be destroyed. We're still working on clearing the rubble, but it's taking too long, those relics have to be recovered before they destroyed forever. Please, hero, find as many relics as you can, but be careful! There's a lot of loose rubble, don't get caught by surprise.I had hoped I'd be able to reward you handsomely, but unfortunately I don't have a lot left.", "You there, traveler. I need a favor of you.I know your efforts were a big driving force in pushing back the enemy, but I'm afraid I must ask even more of you now. It's true, the enemy is on the run, but we'd be fools to let them run and regroup. Hero, I need you to do the dirty work. Seek them out and get rid of those nasty trolls. I'd love to join you, but it'll be up to you. I don't want to get in the way.They'll be no match for you, those trolls. Kill as many of them as you'd like, just make sure you don't forget your main goal.I hope you're not looking for a big reward, but we'll still be able to reward you with somethind decent. Make haste hero, but tread carefully.", "You there, champion. You're a sight for sore eyes.I'm afraid some of our scouts have been captured. We never leave one of our own behind, so we must free them, but sending in a small army will be the death of them for sure. Those monsters will kill our scouts once they see us, they take no risks. So we need you to go on your own, infiltrate their camp and save our troops. Curse those ugly aliens. I wish I could join you, but alas I cannot. Know that I've put all my faith in you, you will succeed. I know it.Please, take care of those aliens. Killing them all won't be needed of course, I'm sure your skills will do enough damage to them.It pains me to say I won't be able to reward you with a lot, but I'll do my best to make it worth your while. I'm looking forward to your return, hero. Good luck.", "Excuse me, champion. Hear me out for a minute.There was once a noble order of druids who lived in the nearby forest. They still live there, but they are no longer noble, far from it. Their rituals seem more demonic than anything else and we cannot let them go on. Hero, please get rid of those disgusting lowlives. I won't be able to join you, but I have full confidence in you. I know you'll succeed.Be strong and swift when fighting the lowlives. There's no need to kill all of them, just put the fear in them.Unfortunately there's not much I have to reward you with, but I think there's a few bits and pieces left. Make haste hero, but tread carefully.", "Pardon me, hero. Would you lend me your aid.We're completely overwhelmed. The war is at its peak and more and more injured come into our hospital in search of aid, but we're running low in supplies. None ofus can afford to leave the injured to seek out more supplies, so we need you to do so. Please gather Peaceblossom, sea urchin needles, silkweb and honey.I will be able to reward you greatly, if you decide to help me of course.", "Please, hero. Spare me some of your time.I'm surrounded by cowards. We were robbed by a group of vermin, they took all they could and killed most of those who tried to stop them. I did what I could, but if everybody helped we would've overpowered them easily. Hero, please retrieve our goods and get rid of those treacherous miscreants. My duties prevent me from going with you, but I doubt I'd be needed anyway.I'm sure you're capable to deal with those miscreants. Make sure they regret the day they ever set foot in our lives. I have little possessions left, but I'll reward you with what I have. Blessed journey champion, may you return swiftly and in one piece."]
quests = []
for x in range(10000):
    quests.append(Quests(IdCharacter=random.choice(characters_ids),
                         QuestDescription=random.choice(QUEST_DESCRIPTION)))
session.add_all(quests)
session.commit()

SKILLS = [("Archaeology", "Archaeologists must travel around the world, looking for fragments of ancient artifacts, many of which have interesting tidbits of lore attached. In Mists of Pandaria, a new faction has been added that loves archaeology."), ("Cooking", "Cooking is a secondary profession which allows players to combine various ingredients such as fish or meat into delicious food and drink items which can be consumed to restore health and mana, as well as to grant combat buffs."), ("Draenor Cooking", ""), ("Fishing", "Fishing is a gathering secondary profession which allows players to fish in the various bodies of water found across the world. Different areas require certain skill levels before the player will be able to cast in the waters."), ("Riding", "Riding is the passive skill which allows players to utilize a mount to navigate throughout the world. Riding skill is not leveled but rather trained at increasing gold costs."), ("Axes", "Ability to use Axes."), ("Bows", "ne of the four ranged weapon-classes. Uses arrows as ammo. No longer benefits from the quiver's effect."), ("Crossbows", "Ability allowing to use Crossbows."), ("Polearms", "Polearms are most of the time designed for Hunters and Feral Druids. In the past these weapons were also designed for warriors and retribution paladins but those classes have swung over to swords, axes and maces."), ("Ancestral Spirit", "Returns the spirit to the body, restoring a dead target to life with 65 health and 120 mana. Cannot be cast when in combat."), ("Chain Heal", "Heals the friendly target for 320 to 369, then jumps to heal additional nearby targets."), ("Chain Lightning", "Hurls a lightning bolt at the enemy, dealing 493 to 552 Nature damage and then jumping to additional nearby enemies."), ("Cure Poison", "Cures 1 poison effect on the target."), ("Earth Shock", "Instantly shocks the target with concussive force, causing 32 to 35 Nature damage."), ("Fire Resistance", "Fire Resistance increased by 30."), ("Flame Shock", "Instantly sears the target with fire, causing 21 Fire damage immediately and 28 Fire damage over 12sec."), ("Flametongue Weapon Proc", "Flametongue Weapon Proc"), ("Frost Shock", "Instantly shocks the target with frost, causing 89 to 96 Frost damage and slowing movement speed by 50%."), ("Grounding Totem", "Summons a Grounding Totem with 5 health at the feet of the caster that will redirect one harmful spell cast on a nearby party member to itself every 10 seconds."), ("Lightning Bolt", "Casts a bolt of lightning at the target for 13 to 16 Nature damage."), ("Lightning Shield", "The caster is surrounded by 3 balls of lightning. When a spell, melee or ranged attack hits the caster, the attacker will be struck for 51 Nature damage."), ("Purge", "Purges the enemy target, removing 2 beneficial magic effects."), ("Tremor Totem", "Summons a Tremor Totem with 5 health at the feet of the caster that shakes the ground around it."), ("Eviscerate", "Finishing move that causes damage per combo point, increased by Attack Power."), ("Cheap Shot", "Stuns the target for 4sec. Must be stealthed. Awards 2 combo points."), ("Mana Tide Totem", "Summons a Mana Tide Totem with 5 health at the feet of the caster for 12sec that restores 290 mana every 3 seconds to group members within 20 yards."), ("Mana Spring", "Gain 6 mana every 2 seconds."), ("Magma Totem", "Summons a Magma Totem with 5 health at the feet of the caster for 20sec that causes 22 Fire damage to creatures within 8 yards every 2 seconds."), ("Rupture", "Finishing move that Garrote", "Garrote the enemy, causing 444 damage over 18sec, increased by your Attack Power."), ("Stealth", "Allows the rogue to sneak around, but reduces your speed by 50%. Lasts until cancelled."), ("Throw", "Hurl a thrown weapon at the target."), ("Seal Fate", "Your critical strikes from abilities that add combo points have a 60% chance to add an additional combo point."), ("Blind", "Blinds the target, causing it to wander confused for up to 10sec. Any damage caused will remove the effect."), ("Pick Pocket", "Pick the target's pocket."), ("Evasion", "The rogue's dodge chance will increase by 50% for 15sec."),
          ("Sprint", "Increases the rogue's movement speed by 50% for 15sec. Does not break stealth."), ("Slice and Dice", "Finishing move that increases melee attack speed by 20%."), ("Sinister Strike", "An instant strike that causes 10 damage in addition to your normal weapon damage. Awards 1 combo point."), ("Ambush", "Ambush the target, causing 250% weapon damage plus 70 to the target. "), ("Poisons", "You can create and mix poisons, with both found and storebought ingredients."), ("Premeditation", "When used, adds 2 combo points to your target."), ("Distract", "Throws a distraction, attracting the attention of all nearby monsters for 10 seconds. "), ("Mind-numbing Poison", "Coats a weapon with poison that lasts for 30 minutes. Each strike has a 20% chance of poisoning the enemy, "), ("Gouge", "Causes 32 damage, incapacitating the opponent for 4sec, and turns off your attack. "), ("Improved Charge", "Increases the amount of rage generated by your Charge ability by 3."), ("Battle Shout", "The warrior shouts, increasing the melee attack power of all party members within 20 yards by 232. Lasts 2min"), ("Berserker Charge", "Charges at an enemy, knocking it back and inflicting normal damage plus 300."), ("Berserker Stance", "An aggressive stance. Critical hit chance is increased by 3% and all damage taken is increased by 10%."), ("Bloodthirst", "Instantly attack the target causing damage equal to 45% of your attack power. I"), ("Booming Voice", "Increases the area of effect and duration of your Battle Shout and Demoralizing Shout by 40%."), ("Cleave", "A sweeping attack that does your weapon damage plus 18 to the target and his nearest ally"), ("Cripple", "Reduces target enemy's attack power by 15 for 30sec"), ("Defensive Stance", "A defensive combat stance. Decreases damage taken by 10% and damage caused by 10%."), ("Demoralizing Shout", "Reduces the melee attack power of nearby enemies by 40 for 30sec."), ("Execute", "Attempt to finish off a wounded foe, causing 125 damage and converting each extra point of rage into 3 additional damage."), ("Hamstring", "Maims the enemy, causing 18 damage and slowing the enemy's movement by 45% for 15sec."), ("Head Butt", "Pummels an enemy for 250 damage and interrupts the spell being cast for 5sec."), ("Improved Battle Shout", "Increases the melee attack power bonus of your Battle Shout by 25%."), ("Conflagrate", "Ignites a target that is already afflicted by Immolate, dealing 240 to 307 Fire damage"), ("Corruption", "Corrupts the target, causing 822 Shadow damage over 18sec."), ("Curse of Agony", "Curses the target with agony, causing 504 Shadow damage over 24sec."), ("Curse of Recklessness", "Curses the target with recklessness, increasing melee attack power by 45 but reducing armor by 290 for 2min."), ("Curse of Weakness", "Damage caused by the target is reduced by 15 for 2min. "), ("Devour Magic", "Purges 1 harmful magic effect from a friend or 1 beneficial magic effect from an enemy. "), ("Drain Mana", "Transfers 99 Mana every 1 sec from the target to the caster. Lasts 5sec."), ("Enslave Demon", "Enslaves the target demon, up to level 47, forcing it to do your bidding. "), ("Fire Shield", "Surrounds the target in a shield of fire. "), ("Firebolt", "Shoots a bolt of flame at an enemy, inflicting Fire damage."), ("Health Funnel", "Gives 119 health to the caster's pet every second for 10sec as long as the caster channels."), ("Immolate", "Burns the enemy for 8 Fire damage and then an additional 20 Fire damage over 15sec."), ("Aimed Shot", "An aimed shot that increases ranged damage by 600."), ("Aspect of the Hawk", "The hunter takes on the aspects of a hawk, increasing Ranged Attack Power by 50."), ("Counterattack", "A strike that becomes active after parrying an opponent's attack. "), ("Immolation Trap", "Place a fire trap that will burn the first enemy to approach for 0 Fire damage over 15sec"), ("Mongoose Bite", "Counterattack the enemy for 25 damage. Can only be performed after you dodge."), ("Raptor Strike", "A strong attack that increases melee damage by 11."), ("Expose Armor", "Finishing move that exposes the target for 30sec, reducing armor per combo point.")]
skills = []

for skill in SKILLS:
    skills.append(Skills(SkillName=skill[0], SkillDescription=skill[1]))
session.add_all(skills)
session.commit()


rounds = []
for x in range(10000):
    rounds.append(Rounds())
session.add_all(rounds)
session.commit()


rounds_ids = [v[0] for v in session.query(Rounds.IdRound).all()]
ROLLS = [(10, 3), (1, 1), (10, 2), (6, 2), (6, 1), (13, 3), (11, 3), (7, 2), (11, 2), (2, 1), (12, 3), (6, 1), (11, 3), (3, 1), (6, 1), (3, 1), (18, 3), (8, 3), (21, 3), (5, 1), (8, 2), (14, 3), (7, 1), (8, 2), (13, 3), (4, 2), (7, 3), (6, 1), (3, 2), (14, 3), (6, 1), (17, 3), (8, 3), (6, 3), (7, 2), (1, 1), (7, 1), (3, 1), (3, 1), (12, 2), (6, 2), (7, 1), (12, 2), (3, 1), (21, 3), (10, 2), (4, 1), (6, 1), (1, 1), (3, 1), (4, 1), (7, 1), (6, 2), (4, 2), (12, 3), (10, 2), (6, 1), (11, 2), (4, 1), (5, 1), (5, 1), (6, 3), (7, 3), (16, 3), (2, 1), (1, 1), (5, 2), (5, 2), (6, 1), (6, 2), (3, 1), (8, 2), (6, 2), (2, 2), (5, 2), (9, 2), (18, 3), (9, 2), (3, 1), (3, 1), (7, 2), (15, 3), (8, 2), (5, 3), (13, 2), (5, 1), (19, 3), (21, 3), (3, 1), (3, 3), (14, 3), (15, 3), (9, 2), (5, 2), (1, 1), (4, 1), (8, 2), (12, 3), (2, 1), (1, 1), (7, 1), (13, 3), (6, 1), (20, 3), (10, 2), (13, 3), (4, 1), (3, 1), (2, 2), (6, 2), (6, 1), (7, 3), (5, 1), (6, 3), (8, 2), (8, 3), (11, 3), (7, 3), (9, 3), (2, 1), (11, 3), (5, 1), (17, 3), (8, 2), (2, 1), (7, 1), (4, 3), (18, 3), (5, 2), (1, 1), (17, 3), (6, 2), (2, 1), (6, 1), (6, 3), (7, 1), (7, 2), (6, 3), (3, 1), (5, 1), (12, 3), (7, 2), (3, 1), (18, 3), (8, 2), (7, 1), (11, 2), (9, 2), (3, 2), (9, 3), (6, 1), (10, 3), (7, 1), (7, 1), (5, 2), (14, 2), (17, 3), (1, 1), (12, 2), (4, 2), (4, 1), (4, 2), (9, 2), (7, 3), (4, 3), (7, 2), (11, 3), (1, 1), (7, 1), (4, 1), (5, 2), (2, 1), (6, 2), (6, 1), (7, 1), (20, 3), (11, 2), (5, 3), (6, 1), (6, 3), (2, 1), (11, 2), (11, 3), (11, 2), (17, 3), (20, 3), (3, 3), (1, 1), (2, 2), (7, 1), (7, 2), (11, 3), (2, 2), (3, 1), (12, 2), (11, 2), (8, 3), (21, 3), (10, 2), (10, 3), (2, 1), (7, 2), (6, 3), (13, 2), (7, 3), (6, 2), (4, 1), (3, 1), (3, 1), (13, 2), (3, 2), (1, 1), (21, 3), (1, 1), (9, 2), (4, 1), (11, 2), (6, 1), (11, 2), (6, 1), (4, 1), (10, 2), (14, 2), (3, 1), (3, 1), (8, 3), (2, 2), (13, 3), (6, 3), (5, 1), (7, 1), (19, 3), (11, 2), (7, 3), (10, 2), (10, 3), (3, 2), (12, 2), (5, 3), (7, 2), (6, 3), (18, 3), (6, 2), (6, 1), (6, 1), (8, 2), (1, 1), (11, 2), (13, 3), (14, 2), (7, 3), (2, 2), (4, 1), (2, 2), (19, 3), (15, 3), (9, 2), (2, 1), (18, 3), (2, 1), (3, 1), (6, 1), (17, 3), (9, 2), (3, 2), (2, 1), (20, 3), (5, 1), (4, 2), (3, 1), (14, 2), (19, 3), (6, 1), (1, 1), (9, 2), (1, 1), (3, 1), (3, 3), (5, 1), (14, 3), (7, 1), (7, 1), (7, 1), (9, 2), (7, 1), (10, 3), (6, 2), (2, 1), (13, 2), (19, 3), (2, 2), (2, 1), (14, 2), (5, 3), (9, 2), (7, 3), (4, 1), (10, 2), (5, 1), (3, 3), (11, 2), (8, 3), (2, 1), (1, 1), (7, 3), (1, 1), (14, 3), (4, 1), (19, 3), (4, 2), (9, 2), (11, 3), (9, 3), (7, 2), (6, 1), (16, 3), (20, 3), (13, 2), (1, 1), (3, 3), (8, 2), (4, 1), (5, 1), (8, 2), (6, 1), (6, 2), (6, 2), (1, 1), (3, 2), (5, 2), (10, 2), (4, 3), (14, 3), (12, 3), (1, 1), (8, 2), (10, 3), (5, 1), (8, 3), (14, 2), (5, 1), (2, 1), (8, 2), (7, 1), (7, 1), (13, 2), (3, 2), (7, 2), (14, 2), (4, 1), (16, 3), (11, 2), (16, 3), (4, 2), (13, 2), (15, 3), (3, 1), (4, 1), (4, 2), (21, 3), (4, 2), (12, 2), (5, 1), (12, 2), (20, 3), (11, 3), (13, 2), (20, 3), (11, 2), (13, 2), (10, 2), (7, 1), (8, 2), (13, 2), (1, 1), (13, 2), (5, 1), (9, 3), (6, 1), (4, 2), (14, 2), (21, 3), (10, 3), (12, 2), (7, 1), (13, 3), (8, 2), (7, 2), (10, 2), (7, 3), (5, 1), (5, 3), (6, 1), (3, 2), (2, 1), (6, 2), (6, 1), (4, 1), (3, 1), (1, 1), (15, 3), (8, 2), (4, 2), (15, 3), (4, 2), (6, 2), (20, 3), (12, 2), (11, 2), (7, 1), (1, 1), (9, 3), (20, 3), (4, 1), (5, 3), (2, 1), (12, 3), (1, 1), (8, 2), (8, 3), (1, 1), (7, 1), (9, 2), (4, 3), (20, 3), (2, 2), (6, 2), (1, 1), (11, 3), (13, 2), (4, 1), (6, 1), (5, 3), (15, 3), (1, 1), (11, 2), (8, 2), (11, 2), (8, 2), (5, 1), (6, 2), (14, 2), (7, 1), (7, 1), (11, 2), (2, 1), (3, 1), (7, 1), (13, 3), (5, 1), (1, 1), (14, 3), (7, 2), (17, 3), (6, 3), (14, 3), (10, 2), (5, 3), (4, 2), (3, 1), (9, 2), (12, 2), (4, 3), (5, 3), (7, 1), (14, 2), (13, 2), (3, 2), (18, 3), (17, 3), (10, 3), (8, 3), (21, 3), (3, 2), (6, 2), (20, 3), (5, 1), (6, 1), (7, 1), (4, 1), (20, 3), (1, 1), (13, 3), (6, 3), (3, 1), (6, 1), (4, 2), (4, 1), (15, 3), (7, 3), (14, 3), (17, 3), (2, 1), (1, 1), (14, 3), (6, 1), (4, 1), (4, 3), (3, 2), (5, 3),
         (8, 3), (13, 3), (21, 3), (7, 2), (11, 2), (5, 1), (3, 2), (9, 3), (5, 1), (3, 1), (12, 2), (7, 2), (13, 2), (5, 3), (4, 1), (8, 2), (20, 3), (1, 1), (7, 2), (16, 3), (4, 3), (6, 2), (6, 1), (9, 2), (2, 2), (6, 1), (14, 2), (3, 1), (4, 1), (6, 1), (1, 1), (12, 2), (8, 3), (8, 2), (2, 2), (17, 3), (4, 2), (6, 1), (8, 2), (5, 2), (6, 1), (6, 2), (3, 1), (21, 3), (11, 3), (7, 3), (9, 2), (12, 3), (6, 1), (4, 1), (14, 3), (6, 1), (5, 2), (7, 2), (2, 1), (8, 2), (2, 2), (7, 3), (2, 1), (14, 2), (14, 3), (7, 1), (3, 2), (4, 1), (14, 2), (4, 2), (5, 1), (7, 1), (3, 1), (14, 3), (17, 3), (7, 2), (5, 2), (13, 3), (2, 1), (7, 1), (10, 2), (10, 3), (5, 2), (9, 2), (10, 2), (20, 3), (4, 3), (2, 1), (8, 3), (1, 1), (11, 3), (4, 2), (6, 2), (11, 2), (14, 3), (7, 1), (4, 1), (18, 3), (3, 3), (5, 2), (7, 3), (7, 1), (5, 1), (7, 2), (11, 2), (2, 1), (2, 2), (10, 2), (14, 2), (6, 1), (6, 1), (1, 1), (1, 1), (21, 3), (17, 3), (16, 3), (1, 1), (7, 1), (11, 2), (14, 3), (3, 3), (13, 3), (10, 3), (20, 3), (4, 2), (3, 1), (13, 2), (13, 3), (7, 2), (6, 1), (15, 3), (8, 3), (2, 2), (8, 3), (5, 3), (1, 1), (4, 1), (11, 2), (13, 2), (17, 3), (12, 3), (12, 3), (1, 1), (3, 1), (11, 2), (3, 2), (8, 3), (8, 2), (9, 2), (3, 2), (11, 2), (14, 3), (6, 3), (5, 3), (3, 2), (3, 1), (3, 3), (4, 1), (7, 1), (4, 1), (3, 1), (11, 2), (2, 1), (10, 3), (7, 1), (4, 1), (8, 3), (13, 2), (10, 2), (11, 2), (3, 3), (21, 3), (2, 1), (4, 1), (21, 3), (2, 1), (4, 1), (14, 3), (7, 1), (10, 3), (3, 1), (5, 1), (3, 1), (12, 2), (20, 3), (14, 3), (15, 3), (7, 2), (7, 1), (7, 1), (2, 1), (2, 1), (17, 3), (12, 2), (4, 1), (8, 3), (12, 2), (7, 1), (3, 3), (3, 1), (2, 1), (9, 2), (16, 3), (4, 3), (11, 3), (12, 3), (5, 2), (7, 1), (5, 2), (1, 1), (13, 2), (7, 2), (2, 1), (7, 1), (7, 3), (2, 2), (5, 1), (4, 1), (4, 1), (5, 1), (14, 3), (13, 2), (7, 1), (14, 2), (6, 1), (21, 3), (21, 3), (9, 2), (2, 1), (4, 2), (6, 3), (11, 2), (8, 3), (3, 1), (8, 3), (3, 3), (8, 3), (4, 1), (11, 3), (5, 3), (13, 2), (5, 2), (8, 2), (4, 1), (19, 3), (5, 2), (3, 1), (2, 1), (2, 1), (2, 1), (8, 2), (7, 2), (5, 1), (3, 1), (10, 2), (16, 3), (13, 2), (12, 2), (3, 1), (13, 2), (7, 1), (4, 2), (3, 1), (3, 2), (13, 3), (4, 3), (6, 1), (5, 2), (1, 1), (11, 3), (7, 2), (5, 2), (11, 2), (7, 3), (14, 2), (13, 2), (8, 3), (14, 2), (3, 1), (1, 1), (18, 3), (8, 2), (12, 2), (12, 3), (13, 3), (4, 3), (7, 3), (1, 1), (3, 2), (10, 2), (7, 1), (18, 3), (3, 1), (13, 2), (5, 1), (11, 2), (19, 3), (10, 2), (2, 1), (12, 2), (2, 1), (10, 3), (3, 1), (4, 3), (20, 3), (10, 2), (13, 2), (3, 1), (4, 3), (6, 1), (7, 1), (3, 2), (1, 1), (4, 1), (8, 2), (13, 3), (14, 2), (1, 1), (17, 3), (21, 3), (7, 1), (13, 3), (10, 2), (5, 1), (1, 1), (1, 1), (14, 2), (12, 2), (6, 1), (5, 1), (3, 2), (4, 1), (5, 1), (3, 3), (4, 2), (6, 2), (9, 2), (21, 3), (6, 1), (10, 2), (7, 1), (7, 2), (7, 2), (13, 2), (8, 3), (2, 1), (13, 2), (14, 2), (12, 3), (5, 1), (4, 1), (19, 3), (12, 2), (19, 3), (13, 3), (6, 2), (11, 2), (1, 1), (4, 3), (15, 3), (4, 1), (3, 1), (5, 1), (3, 3), (3, 2), (4, 1), (4, 1), (11, 2), (4, 1), (13, 2), (11, 3), (3, 1), (6, 1), (6, 3), (9, 2), (16, 3), (12, 3), (10, 2), (5, 2), (4, 2), (21, 3), (4, 1), (15, 3), (8, 3), (3, 1), (11, 2), (14, 3), (1, 1), (6, 2), (20, 3), (7, 2), (7, 1), (7, 2), (7, 3), (6, 1), (14, 2), (3, 2), (9, 2), (12, 3), (2, 2), (2, 1), (5, 1), (7, 1), (3, 1), (12, 2), (5, 3), (1, 1), (6, 2), (4, 1), (11, 2), (5, 2), (6, 3), (14, 2), (8, 3), (5, 2), (2, 1), (13, 3), (5, 3), (3, 2), (2, 1), (18, 3), (20, 3), (2, 1), (4, 2), (10, 2), (7, 2), (14, 2), (5, 1), (11, 3), (5, 1), (7, 1), (9, 2), (1, 1), (14, 2), (10, 2), (3, 1), (11, 3), (7, 2), (3, 1), (10, 2), (7, 1), (4, 1), (11, 3), (3, 1), (7, 3), (17, 3), (9, 3), (16, 3), (7, 3), (8, 2), (9, 3), (1, 1), (9, 2), (7, 2), (7, 2), (5, 2), (6, 3), (4, 1), (4, 2), (4, 1), (6, 2), (11, 2), (7, 2), (10, 2), (2, 1), (8, 3), (6, 2), (7, 2), (9, 2), (4, 1), (7, 3), (6, 2), (1, 1), (8, 2), (13, 3), (7, 1), (9, 3), (8, 3), (12, 3), (5, 3), (7, 3), (5, 3), (11, 2), (9, 3), (13, 2), (2, 2), (6, 2), (15, 3), (3, 1), (3, 3), (4, 3), (10, 3), (4, 1), (12, 2), (19, 3), (14, 2), (8, 2), (21, 3), (11, 3), (3, 1), (1, 1), (4, 3), (10, 2), (7, 1)]
rolls = []
for roll in ROLLS:
    rolls.append(
        Rolls(Dice=roll[0], DiceAmount=roll[1], IdRound=random.choice(rounds_ids)))
session.add_all(rolls)
session.commit()


treasueres = []
for x in range(10000):
    treasueres.append(Treasures())
session.add_all(treasueres)
session.commit()

EQ = ["Curse Mantle", "Eternity Necklace", "Supremacy Cylinder", "Guardian's Shield", "Wisdom Urn", "Elixir of Damnation", "Necklace of Evil", "Ark of Decadence", "Staff of Betrayal", "Statue of Decay", "Lust's Runes", "Faith's Symbols", "Shadow Gem", "Hungering Stone", "Echo Goblet", "Tome of Shapeshifting", "Jar of Runes", "Circlet of Blights", "Cloak of Genesis", "Jar of Vengeance", "Fate's Symbols", "Purity Boots", "Sanctifying Scroll", "Anarchy Skull", "Athanasia Ark", "Statue of Oblivion", "Horn of Pain", "Mirror of Apathy", "Staff of Torture", "Urn of Dread", "Lucky Sword", "Spite Tome", "Massacre Mirror", "Decimation Ring", "Elemental Gauntlet", "Box of Karma", "Box of the Scourge", "Robes of Karma", "Book of Wishes", "Mirror of Malady", "Lifeblood Goblet", "Prosperous Seal", "Temptation Fleece", "Demonic Ichor", "Destruction Rod", "Shield of Oaths", "Skull of Chance", "Chest of Speed", "Ark of the Occult", "Shard of Strength", "Binding Robes", "Paramount Ring", "Anguish Box", "Rogue Pillar", "Kismet Feather", "Cloak of Shapeshifting", "Monolith of Death", "Ichor of Mania", "Ichor of Toxin", "Cube of Secrets", "Massacre Cylinder", "Burning Gauntlet", "Twilight Microlith", "Elemental Symbols", "Holy Root", "Seal of Riddles", "Box of Grace", "Tablet of Service", "Arch of Paradise", "Canopic", "Jar of Genesis", "Fortitude Tome", "Equinox Feather", "Chaos Seal", "Sentient Cube",
      "Letters of Chaos", "Grail of Lightning", "Tablet of Speed", "Lamp of Riddles", "Staff of Misfortune", "Prime Horn", "Truth Stone", "Seduction Tome", "Resistance Ichor", "Shade Goblet", "Circlet of Ice", "Robes of Youth", "Crown of Apathy", "Tiara of Valor", "Hide of Hell", "Maniacal Runes", "Impurity Mantle", "Devotion Seal", "Void Urn", "Cup of the Gods", "Robes of Paradise", "Key of Passion", "Sword of Malice", "Dominance Boots", "Death's Monolith", "Supremacy Door", "Enigma Band", "Elemental Feather", "Amulet of Enthrallment", "Statuette of Spells", "Chest of Spellbinding", "Ichor of Valor", "Bracelet of Agony", "Necrotic Shield", "Genesis Boots", "Black Magic", "Mirror", "Paradise Door", "Healing Fruit", "Robes of Dreams", "Tablet o Rebirth", "Boots of Sentience", "Band of Betrayal", "Arch of Vice", "Termination Disc", "Aurora Cylinder", "Tribute Instrument", "Exile Skull", "Tiara of Binding", "Cylinder of Eternal Slumber", "Seal of Storms", "Rod of the Aether", "Root of Toxin", "Paradise Slab", "Valor Goblet", "Teleporting Inscriptions", "Kismet Tiara", "Valor Cloak", "Grimoire of the Oracle", "Staff of Gold", "Chalice of Fertility", "Sandals of Fortune", "Tome of Enthrallment", "Resolution Disc", "Thunder Cup", "Infinity Tablet", "Justice Disc", "Robes of Downfall", "Grail of All-Seeing", "Cloak of the Aether", "Sandals of Immobilizing", "Monolith of Torment"]
eqs = []
for eq in EQ:
    # print(eq)
    eqs.append(Equipments(EquipmName=eq, EquipmValue=randint(
        0, 30000), EquipmDescription=eq))
session.add_all(eqs)
session.commit()

###################################
# ASSOCIATION TABLES
###################################

action_char = []

for x in range(10000):
    action_char.append(Character_Action(IdCharacter=random.choice(
        characters_ids), IdAction=random.choice(actions_ids)))
session.add_all(action_char)
session.commit()


buff_char = []
for x in range(10000):
    buff_char.append(Character_Buff(IdCharacter=random.choice(
        characters_ids), IdBuff=random.choice(buffs_ids)))
session.add_all(buff_char)
session.commit()

quests_ids = [v[0] for v in session.query(Quests.IdQuest).all()]
quest_act = []
for x in range(10000):
    quest_act.append(Quest_Action(IdAction=random.choice(
        actions_ids), IdQuest=random.choice(quests_ids)))
session.add_all(quest_act)
session.commit()


classes_ids = [v[0] for v in session.query(Classes.IdClass).all()]
race_cla = []
for x in range(10000):
    race_cla.append(Race_Class(IdRace=random.choice(
        races_ids), IdClass=randint(1, 6)))
session.add_all(race_cla)
session.commit()


skills_ids = [v[0] for v in session.query(Skills.IdSkill).all()]
act_skill = []
for x in range(10000):
    act_skill.append(Action_Skill(IdAction=random.choice(
        actions_ids), IdSkill=random.choice(skills_ids)))
session.add_all(act_skill)
session.commit()


char_quest = []
for x in range(10000):
    char_quest.append(Character_Quest(IdCharacter=random.choice(
        characters_ids), IdQuest=random.choice(quests_ids)))
session.add_all(char_quest)
session.commit()


specs_ids = [v[0] for v in session.query(Specializations.IdSpec).all()]
char_spec = []
for x in range(10000):
    char_spec.append(Character_Specialization(IdCharacter=random.choice(
        characters_ids), IdSpec=random.choice(specs_ids)))
session.add_all(char_spec)
session.commit()


treasueres_ids = [v[0] for v in session.query(Treasures.IdTreasure).all()]

treasure_ques = []
for x in range(10000):
    treasure_ques.append(Treasure_Quest(IdQuest=random.choice(
        quests_ids), IdTreasure=random.choice(treasueres_ids)))
session.add_all(treasure_ques)
session.commit()


treasure_fiel = []
for x in range(10000):
    treasure_fiel.append(Treasure_Field(IdField=random.choice(
        fields_ids), IdTreasure=random.choice(treasueres_ids)))
session.add_all(treasure_fiel)
session.commit()

rolls_ids = [v[0] for v in session.query(Rolls.IdRoll).all()]

roll_skill = []
for x in range(10000):
    roll_skill.append(Roll_Skill(IdRoll=random.choice(
        rolls_ids), IdSkill=random.choice(skills_ids)))
session.add_all(roll_skill)
session.commit()


equips_ids = [v[0] for v in session.query(Equipments.IdEquipm).all()]
zones_ids = [v[0] for v in session.query(Zones.IdZone).all()]

eq_mod = []
for x in range(10000):
    eq_mod.append(Equipment_Modifier(IdMod=random.choice(
        mod_ids), IdEquipm=random.choice(equips_ids), ModifierValue=randint(1, 20)))
session.add_all(eq_mod)

session.commit()

eq_char = []
for x in range(10000):
    eq_char.append(Character_Equipment(IdCharacter=random.choice(
        characters_ids), IdEquipm=random.choice(equips_ids), Amount=randint(0, 3)))
session.add_all(eq_char)
session.commit()


eq_tres = []
for x in range(10000):
    eq_tres.append(Treasure_Equipment(IdTreasure=random.choice(
        treasueres_ids), IdEquipm=random.choice(equips_ids)))
session.add_all(eq_tres)
session.commit()


spec_skill = []
for x in range(10000):
    spec_skill.append(Specialization_Skill(IdSpec=random.choice(
        specs_ids), IdSkill=random.choice(skills_ids)))
session.add_all(spec_skill)
session.commit()

# FILL THE EQUPIMENT_ZONE TABLE
# char_zone = []
# for x in range(10000):
#     char_zone.append(Character_Zone_Equipment(IdCharacter=random.choice(
#         characters_ids), IdZone=random.choice(zones_ids), IdEquipm=random.choice(equips_ids)))
#     session.add_all(char_zone)
# session.commit()

char_zone = []
for ch_id in characters_ids:
    for zo_id in zones_ids:
        if random.randint(1, 10) < 7:
            char_zone.append(Character_Zone_Equipment(
                IdCharacter=ch_id, IdZone=zo_id, IdEquipm=random.choice(equips_ids)))
            break
    session.add_all(char_zone)
session.commit()


# FILL THE EQUPIMENT_ZONE TABLE
equ_zone = []
for x in range(10000):
    equ_zone.append(Equipment_Zone(IdEquipm=random.choice(
        equips_ids), IdZone=random.choice(zones_ids)))
    # print(x)
    session.add_all(equ_zone)
session.commit()
connection.close()


# # FILL THE BUFFS TABLE
mod_buff = []
for x in range(10000):
    mod_buff.append(Buff_Modifier(ModifierValue=randint(-8, 9),
                                  IdMod=random.choice(mod_ids), IdBuff=random.choice(buffs_ids)))
session.add_all(mod_buff)
session.commit()


rounds_ids = [v[0] for v in session.query(Rounds.IdRound).all()]

rounds_summaries = []
for x in range(10000):
    rounds_summaries.append(
        RoundSummary(IdRound=random.choice(rounds_ids), 
                        IdRoll=random.choice(rolls_ids), 
                        IdCharacter=random.choice(characters_ids), 
                        CharacterHealth=random.randint(1, 500), 
                        IdAction=random.choice(actions_ids), 
                        IdTarget=random.choice(fields_ids),
                        DealtDamage=random.randint(1, 30),
                        TakenDamage=random.randint(1, 30)))
session.add_all(rounds_summaries)
session.commit()
