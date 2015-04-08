# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table audio (
  id                        bigint auto_increment not null,
  lien                      varchar(255),
  objet_id                  bigint,
  constraint pk_audio primary key (id))
;

create table contenu_site (
  id                        bigint auto_increment not null,
  contenu                   TEXT,
  constraint pk_contenu_site primary key (id))
;

create table favori (
  id                        bigint auto_increment not null,
  utilisateur_id            bigint,
  objet_id                  bigint,
  constraint pk_favori primary key (id))
;

create table feedback (
  id                        bigint auto_increment not null,
  contenu                   TEXT,
  email                     varchar(255),
  creation_date             datetime,
  constraint pk_feedback primary key (id))
;

create table historique (
  id                        bigint auto_increment not null,
  utilisateur_id            bigint,
  objet_id                  bigint,
  constraint pk_historique primary key (id))
;

create table image (
  id                        bigint auto_increment not null,
  lien                      varchar(255),
  objet_id                  bigint,
  constraint pk_image primary key (id))
;

create table objet (
  id                        bigint auto_increment not null,
  nom                       varchar(255),
  reference                 varchar(255),
  description               TEXT,
  type                      varchar(255),
  matiere                   varchar(255),
  largeur                   double,
  longeur                   double,
  hauteur                   double,
  poids                     double,
  localisation_actuelle     varchar(255),
  localisation_origine      varchar(255),
  archeologue               varchar(255),
  date_decouverte           datetime,
  civilisation              varchar(255),
  model3d                   varchar(255),
  creation_date             datetime,
  constraint pk_objet primary key (id))
;

create table parcours (
  id                        bigint auto_increment not null,
  nom                       varchar(255),
  constraint pk_parcours primary key (id))
;

create table parcours_objet (
  id                        bigint auto_increment not null,
  objet_id                  bigint,
  parcours_id               bigint,
  constraint pk_parcours_objet primary key (id))
;

create table utilisateur (
  id                        bigint auto_increment not null,
  email                     varchar(255),
  password                  varchar(255),
  username                  varchar(255),
  rights                    varchar(255),
  creation_date             datetime,
  constraint pk_utilisateur primary key (id))
;

create table video (
  id                        bigint auto_increment not null,
  lien                      varchar(255),
  objet_id                  bigint,
  constraint pk_video primary key (id))
;


create table statistique_objet (
  objet1                         bigint not null,
  objet2                         bigint not null,
  constraint pk_statistique_objet primary key (objet1, objet2))
;

create table oeuvre_composite (
  oeuvre_principale              bigint not null,
  oeuvre_inspiree                bigint not null,
  constraint pk_oeuvre_composite primary key (oeuvre_principale, oeuvre_inspiree))
;
alter table audio add constraint fk_audio_objet_1 foreign key (objet_id) references objet (id) on delete restrict on update restrict;
create index ix_audio_objet_1 on audio (objet_id);
alter table favori add constraint fk_favori_utilisateur_2 foreign key (utilisateur_id) references utilisateur (id) on delete restrict on update restrict;
create index ix_favori_utilisateur_2 on favori (utilisateur_id);
alter table favori add constraint fk_favori_objet_3 foreign key (objet_id) references objet (id) on delete restrict on update restrict;
create index ix_favori_objet_3 on favori (objet_id);
alter table historique add constraint fk_historique_utilisateur_4 foreign key (utilisateur_id) references utilisateur (id) on delete restrict on update restrict;
create index ix_historique_utilisateur_4 on historique (utilisateur_id);
alter table historique add constraint fk_historique_objet_5 foreign key (objet_id) references objet (id) on delete restrict on update restrict;
create index ix_historique_objet_5 on historique (objet_id);
alter table image add constraint fk_image_objet_6 foreign key (objet_id) references objet (id) on delete restrict on update restrict;
create index ix_image_objet_6 on image (objet_id);
alter table parcours_objet add constraint fk_parcours_objet_objet_7 foreign key (objet_id) references objet (id) on delete restrict on update restrict;
create index ix_parcours_objet_objet_7 on parcours_objet (objet_id);
alter table parcours_objet add constraint fk_parcours_objet_parcours_8 foreign key (parcours_id) references parcours (id) on delete restrict on update restrict;
create index ix_parcours_objet_parcours_8 on parcours_objet (parcours_id);
alter table video add constraint fk_video_objet_9 foreign key (objet_id) references objet (id) on delete restrict on update restrict;
create index ix_video_objet_9 on video (objet_id);



alter table statistique_objet add constraint fk_statistique_objet_objet_01 foreign key (objet1) references objet (id) on delete restrict on update restrict;

alter table statistique_objet add constraint fk_statistique_objet_objet_02 foreign key (objet2) references objet (id) on delete restrict on update restrict;

alter table oeuvre_composite add constraint fk_oeuvre_composite_objet_01 foreign key (oeuvre_principale) references objet (id) on delete restrict on update restrict;

alter table oeuvre_composite add constraint fk_oeuvre_composite_objet_02 foreign key (oeuvre_inspiree) references objet (id) on delete restrict on update restrict;

# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table audio;

drop table contenu_site;

drop table favori;

drop table feedback;

drop table historique;

drop table image;

drop table objet;

drop table statistique_objet;

drop table oeuvre_composite;

drop table parcours;

drop table parcours_objet;

drop table utilisateur;

drop table video;

SET FOREIGN_KEY_CHECKS=1;

