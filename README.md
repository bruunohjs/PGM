PGM
===

O objetivo deste trabalho é possibilitar que o usuário acompanhe as despesas de seus diversos cartões de crédito, exibindo suas despesas mensais. 

Ao registrar a compra de um produto parcelado, o calendário exibe suas parcelas em cada mês que o tiver. Sendo assim, o usuário conta com uma estimativa de seus gastos em meses futuros.
Caso a fatura do mês já esteja fechada, a despesa adicionada irá entrar automaticamente na fatura do mês seguinte da data informada.

<p align="center">
  <img src="https://github.com/marcellocamara/PGM/blob/master/extras/images/others/giphy.gif" title="Criando um cartão">
</p>

## Análise / Avaliação

O aplicativo será lançado na `Google Play Store` em breve.

Até lá, ele encontra-se disponível para avalição [clicando aqui](https://raw.githubusercontent.com/marcellocamara/PGM/master/extras/APK/PGM.apk).

## Sobre

O incentivo para o desenvolvimento deste projeto foi a necessidade do autor em estudar e praticar o Firebase Realtime Database e o padrão arquitetural MVP (Model View Presenter).

<p align="center">
  <img src="https://github.com/marcellocamara/PGM/blob/master/extras/images/others/MVP.png" height="350" title="Model-View-Presenter">
</p>

## Telas do aplicativo

O design segue as diretrizes do [Material Design](https://material.io/design) e conta com suporte para rotação da orientação da tela, garantindo a integridade da interface em diferentes tamanhos e resoluções de tela.

A tabela abaixo mostra cada nome de tela desenvolvida e seu respectivo link para visualização da print screen da tela.

Nome da tela | Idioma
:---  | :---: 
Login | [pt-BR](https://raw.githubusercontent.com/marcellocamara/PGM/master/extras/images/screenshots/pt-BR/login.png) - [en-US](https://raw.githubusercontent.com/marcellocamara/PGM/master/extras/images/screenshots/en-US/login.png) - [es-ES](https://raw.githubusercontent.com/marcellocamara/PGM/master/extras/images/screenshots/es-ES/login.png)
Cadastrar | [pt-BR](https://raw.githubusercontent.com/marcellocamara/PGM/master/extras/images/screenshots/pt-BR/register.png) - [en-US](https://raw.githubusercontent.com/marcellocamara/PGM/master/extras/images/screenshots/en-US/register.png) - [es-ES](https://raw.githubusercontent.com/marcellocamara/PGM/master/extras/images/screenshots/es-ES/register.png)
Recuperar Senha | [pt-BR](https://raw.githubusercontent.com/marcellocamara/PGM/master/extras/images/screenshots/pt-BR/recover.png) - [en-US](https://raw.githubusercontent.com/marcellocamara/PGM/master/extras/images/screenshots/en-US/recover.png) - [es-ES](https://raw.githubusercontent.com/marcellocamara/PGM/master/extras/images/screenshots/es-ES/recover.png)
Home | [pt-BR](https://raw.githubusercontent.com/marcellocamara/PGM/master/extras/images/screenshots/pt-BR/home.png) - [en-US](https://raw.githubusercontent.com/marcellocamara/PGM/master/extras/images/screenshots/en-US/home.png) - [es-ES](https://raw.githubusercontent.com/marcellocamara/PGM/master/extras/images/screenshots/es-ES/home.png)
Menu | [pt-BR](https://raw.githubusercontent.com/marcellocamara/PGM/master/extras/images/screenshots/pt-BR/menu.png) - [en-US](https://raw.githubusercontent.com/marcellocamara/PGM/master/extras/images/screenshots/en-US/menu.png) - [es-ES](https://raw.githubusercontent.com/marcellocamara/PGM/master/extras/images/screenshots/es-ES/menu.png)
Nova despesa | [pt-BR](https://raw.githubusercontent.com/marcellocamara/PGM/master/extras/images/screenshots/pt-BR/new_expense.png) - [en-US](https://raw.githubusercontent.com/marcellocamara/PGM/master/extras/images/screenshots/en-US/new_expense.png) - [es-ES](https://raw.githubusercontent.com/marcellocamara/PGM/master/extras/images/screenshots/es-ES/new_expense.png)
Despesa | [pt-BR](https://raw.githubusercontent.com/marcellocamara/PGM/master/extras/images/screenshots/pt-BR/expense_overview.png) - [en-US](https://raw.githubusercontent.com/marcellocamara/PGM/master/extras/images/screenshots/en-US/expense_overview.png) - [es-ES](https://raw.githubusercontent.com/marcellocamara/PGM/master/extras/images/screenshots/es-ES/expense_overview.png)
Cartões | [pt-BR](https://raw.githubusercontent.com/marcellocamara/PGM/master/extras/images/screenshots/pt-BR/cards.png) - [en-US](https://raw.githubusercontent.com/marcellocamara/PGM/master/extras/images/screenshots/en-US/cards.png) - [es-ES](https://raw.githubusercontent.com/marcellocamara/PGM/master/extras/images/screenshots/es-ES/cards.png)
Novo cartão | [pt-BR](https://raw.githubusercontent.com/marcellocamara/PGM/master/extras/images/screenshots/pt-BR/new_card.png) - [en-US](https://raw.githubusercontent.com/marcellocamara/PGM/master/extras/images/screenshots/en-US/new_card.png) - [es-ES](https://raw.githubusercontent.com/marcellocamara/PGM/master/extras/images/screenshots/es-ES/new_card.png)
Cartão | [pt-BR](https://raw.githubusercontent.com/marcellocamara/PGM/master/extras/images/screenshots/pt-BR/card_overview.png) - [en-US](https://raw.githubusercontent.com/marcellocamara/PGM/master/extras/images/screenshots/en-US/card_overview.png) - [es-ES](https://raw.githubusercontent.com/marcellocamara/PGM/master/extras/images/screenshots/es-ES/card_overview.png)
Pontos | [pt-BR](https://raw.githubusercontent.com/marcellocamara/PGM/master/extras/images/screenshots/pt-BR/points.png) - [en-US](https://raw.githubusercontent.com/marcellocamara/PGM/master/extras/images/screenshots/en-US/points.png) - [es-ES](https://raw.githubusercontent.com/marcellocamara/PGM/master/extras/images/screenshots/es-ES/points.png)
Perfil | [pt-BR](https://raw.githubusercontent.com/marcellocamara/PGM/master/extras/images/screenshots/pt-BR/profile.png) - [en-US](https://raw.githubusercontent.com/marcellocamara/PGM/master/extras/images/screenshots/en-US/profile.png) - [es-ES](https://raw.githubusercontent.com/marcellocamara/PGM/master/extras/images/screenshots/es-ES/profile.png)

## Ferramentas utilizadas

- [Butter Knife](https://github.com/JakeWharton/butterknife)
- [CardView](https://developer.android.com/guide/topics/ui/layout/cardview)
- [CircularImageView](https://github.com/lopspower/CircularImageView)
- [Firebase Cloud Messaging](https://firebase.google.com/docs/cloud-messaging/)
- [Firebase Realtime Database](https://firebase.google.com/docs/database/)
- [Glide](https://github.com/bumptech/glide)
- [KeyboardVisibilityEvent](https://github.com/yshrsmz/KeyboardVisibilityEvent)
- [Material Calendar View](https://github.com/prolificinteractive/material-calendarview)
- [Material Design](https://material.io/design/)
- [Mutative Fab](https://github.com/aniketbhoite/MutativeFab)
- [RecyclerView](https://developer.android.com/guide/topics/ui/layout/recyclerview)
- [Spots Progress Dialog](https://github.com/d-max/spots-dialog)
- [Tooltips](https://github.com/ViHtarb/Tooltip)

<p align="center">
  <img src="https://github.com/marcellocamara/PGM/blob/master/app/src/main/ic_launcher-web.png" height="128" title="Ícone">
</p>