# OreGen
- - - 
>이 플러그인은 마인크래프트 Java Edition 서버에서 사용되는 플러그인입니다.

커스터마이징이 가능한 광물생성기 플러그인입니다.

## 🖥️ 플러그인 소개
- - -
마인팜에서 주로 사용되는 코블스톤 생성기를 커스텀 할 수 있는 플러그인 입니다.

기본적으로 물 <-> "블록" 간의 접촉시 생성되는 블럭들을 커스텀 할 수 있습니다.


## 📜 플러그인 사용법
- - -
광물이 생성되기 위해선 물이 블록과 접촉해야합니다. 이때의 블록을 "접촉블록" 이라고 하겠습니다.

또한 접촉으로 인해 생성되는 블록을 "생성블록" 이라고 칭하겠습니다.

이 플러그인은, 접촉블록과 생성블록 모두를 커스텀 하는 것이 가능합니다.

접촉블록마다, 생성블록 목록이 존재하며, 확률은 접촉블록마다 독립적으로 설정할 수 있습니다.

### 📄 config.yml
- config.yml 에서는 itemsAdder 플러그인을 통한 커스텀 블록을 사용할지 정할 수 있습니다.
- 기본적으로는, false 이며 true 이더라도 플러그인이 존재하지 않는다면 활성화되지 않습니다.
- itemsAdder 플러그인은 하단의 링크를 참조해 주십시오.
- <https://www.spigotmc.org/resources/%E2%9C%A8itemsadder%E2%AD%90emotes-mobs-items-armors-hud-gui-emojis-blocks-wings-hats-liquids.73355/>
```
# itemsAdder 플러그인 사용시 true, 아니면 false
USE_ITEMS_ADDER: false
```

### 📄 settings.yml 설정방법
- settings.yml 에서는 접촉블록마다의 생성블록의 확률을 지정할 수 있습니다.
- 접촉블록을 지정할 떄는 Material 명으로 지정해야합니다. Material 명은 하단의 링크를 참조해주십시오.
- <https://jd.papermc.io/paper/1.20.4/org/bukkit/Material.html>
- 접촉블록을 itemsAdder 플러그인을 통해 생성한 커스텀 블록으로 지정하는 것도 가능합니다.
- 커스텀 블록 사용시에는 itemsAdder 플러그인을 통해 생성한 커스텀 블록의 이름 앞에, CUSTOM_ 을 붙여 지정합니다.
- 확률은 최대 소수점 세자리까지 지원합니다. 그 이상으로 입력할 경우, 적용되지 않으며 원하지 않는 결과를 얻을 수 있습니다.
```
# 확률은 최대 소수점 3자리
# ex) 0.005, 10.775

# itemsAdder 플러그인으로 생성한 커스텀 블록 이용은
# CUSTOM_name 형식으로 작성. ex) CUSTOM_NEW_BLOCK
# 단, CUSTOM 블록 사용을 위해선, config.yml 에서 USE_ITEMS_ADDER 항목을 true 로 설정

OAK_FENCE:
  STONE: 70
  OAK_WOOD: 30

DARK_OAK_FENCE:
  STONE: 50
  DIAMOND_ORE: 50
```

### 🔧 명령어
명령어는 오직 하나만 존재합니다.
```
/oregen reload - settings.yml 과 config.yml 을 리로드합니다.
```

## ⚠️ 주의사항
- - -
이 플러그인은 Paper 버킷에 포함되어 있는 라이브러리를 사용하였습니다. 

Paper 버킷 이외의 환경에서 구동 시 문제가 발생할 수 있습니다.

## 📘 개발환경 & 사용한 라이브러리
- - -
- `Java 21`
- `io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT`
- `dev.lone:api-itemsadder:4.0.9`