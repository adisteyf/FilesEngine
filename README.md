# Files Engine
### About Files Engine
This is open sourse **2D game** engine on java using `java.awt` for Windows / MacOS / linux.

### Info about releases
In the next release i will add Scene Manager and change library to `LWJGL`. 

#### How colliders work:
- Find center of colliders.
- Subtract from **X** of main collider **X** of movable collider.
- It's the same with **Y**.
- Compare absolute of **X** and **Y**, which is bigger?
  - This is **X** and it's positive = **right** side.
  - This is **X** and it's negative = **left** side.
  - This is **Y** and it's positive = **bottom** side.
  - This is **Y** and it's negative = **top** side.
 
### Plan of **alpha-0.2.0 release**:
- [x] сделать систему передвижения через матрикс
- [ ] подключить к новому движку старые скрипты
- [x] ---- подключить систему скриптов
- [ ] ---- подключить систему колизий
- [x] ---- подключить систему существ
- [x] ---- подключить систему FPS
- [x] ---- подключить систему текстур
- [ ] ---- подключить систему сцен
- [x] ---- подключить систему трансформ
- [ ] закатать релиз alpha-0.2.0

*Version: alpha-0.1.3*

**START fe-collector.sh ONLY IN "content root" (.../IdeaProjects/FilesEngine folder)**

**by [adk.](https://github.com/adisteyf)**
### Соц. сети:
тг: [@shawarmateam](https://t.me/shawarmateam)<br>
дс: [Shawarma Team](discord.gg/8PrbHxjwxv) (discord.gg/8PrbHxjwxv)
