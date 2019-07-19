# Interface editor

This interface is useable for the 530+ revision of runescape cache, with some edits this will also be able to load osrs caches. I released this code to help the community, I wrote this code a long time ago and just recently started with refactoring it. It works but the code isn't the best (working on it :) )


# Usage
## Startup
Open the **config.properties** file. Chang the **cache_path** field to your cache path, make sure to use \\ or / and end with it aswell. After that you can launch the jar file or use your IDE to launch **InterfaceGUI.java**
 > #Tool Properties
	cache_path=D:\\server\\data\\cache\\
	dump_path=dump/
	sprite_path=dump/
	version = 1
  
## Editing interfaces
![picture of the editor](https://cdn.discordapp.com/attachments/320896231293452289/601751648259538944/unknown.png)
You can select an interface on the left, once clicked on it you can selected a component from that interface on the right side.





##	More info
###	Font ids
List of current font ids I found
305 307 468 473 494 495 496 497 584 591 645 646 647 648 764 776 819 1591 2244 2710 3237 3793 3794 3795 4040 5419 5631 13120 13121
###	Found script ids
Will update this later
###	Script info
**How does those scripts work in the script tab ?**

example the transparency script : 1357;-2147483645;100;
The 'code' of the script. (not editable with this editor, you can also change the paramters it receives)
```
void script_1357(Widget widget0, int arg1) {
	widget0.setTrans(arg1);
	return;
}
```
**what do the numbers mean?**

> 1357: is the id of script, the first number in the array is ALWAYS the script id
> everything after 1357 are paremeters for that specific script.
> -2147483645 : means it's the widget itself refering to it's own hash, if you want to trigger another component use it hash.
> 100 : an extra parameter (so the trans will be 100% when hovered on the component)
