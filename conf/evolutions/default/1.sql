# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table audio (
  id                        bigint auto_increment not null,
  lien                      varchar(255),
  nom                       varchar(255),
  objet_id                  integer,
  constraint pk_audio primary key (id))
;

create table commentaire (
  id                        bigint auto_increment not null,
  contenu                   TEXT,
  valide                    tinyint(1) default 0,
  creation_date             datetime,
  utilisateur_id            bigint,
  objet_id                  integer,
  constraint pk_commentaire primary key (id))
;

create table contenu_site (
  id                        bigint auto_increment not null,
  contenu                   TEXT,
  emplacement               varchar(255),
  constraint pk_contenu_site primary key (id))
;

create table fait_historique (
  id                        bigint auto_increment not null,
  contenu                   TEXT,
  valide                    tinyint(1) default 0,
  creation_date             datetime,
  utilisateur_id            bigint,
  objet_id                  integer,
  constraint pk_fait_historique primary key (id))
;

create table favori (
  id                        bigint auto_increment not null,
  utilisateur_id            bigint,
  objet_id                  integer,
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
  objet_id                  integer,
  constraint pk_historique primary key (id))
;

create table image (
  id                        bigint auto_increment not null,
  lien                      varchar(255),
  objet_id                  integer,
  nom                       varchar(255),
  constraint pk_image primary key (id))
;

create table objet (
  id                        integer auto_increment not null,
  nom                       varchar(255),
  reference                 varchar(255),
  description               TEXT,
  type_objet                varchar(255),
  matiere                   varchar(255),
  largeur                   double,
  longueur                  double,
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
  id                        integer auto_increment not null,
  nom                       varchar(255),
  constraint pk_parcours primary key (id))
;

create table parcours_objet (
  id                        bigint auto_increment not null,
  objet_id                  integer,
  parcours_id               integer,
  constraint pk_parcours_objet primary key (id))
;

create table proposition_modification (
  id                        bigint auto_increment not null,
  nom_champ                 varchar(255),
  nouveau_contenu           TEXT,
  creation_date             datetime,
  utilisateur_id            bigint,
  objet_id                  integer,
  constraint pk_proposition_modification primary key (id))
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
  nom                       varchar(255),
  objet_id                  integer,
  constraint pk_video primary key (id))
;


create table statistique_objet (
  objet1                         integer not null,
  objet2                         integer not null,
  constraint pk_statistique_objet primary key (objet1, objet2))
;

create table oeuvre_composite (
  oeuvre_principale              integer not null,
  oeuvre_inspiree                integer not null,
  constraint pk_oeuvre_composite primary key (oeuvre_principale, oeuvre_inspiree))
;
alter table audio add constraint fk_audio_objet_1 foreign key (objet_id) references objet (id) on delete restrict on update restrict;
create index ix_audio_objet_1 on audio (objet_id);
alter table commentaire add constraint fk_commentaire_utilisateur_2 foreign key (utilisateur_id) references utilisateur (id) on delete restrict on update restrict;
create index ix_commentaire_utilisateur_2 on commentaire (utilisateur_id);
alter table commentaire add constraint fk_commentaire_objet_3 foreign key (objet_id) references objet (id) on delete restrict on update restrict;
create index ix_commentaire_objet_3 on commentaire (objet_id);
alter table fait_historique add constraint fk_fait_historique_utilisateur_4 foreign key (utilisateur_id) references utilisateur (id) on delete restrict on update restrict;
create index ix_fait_historique_utilisateur_4 on fait_historique (utilisateur_id);
alter table fait_historique add constraint fk_fait_historique_objet_5 foreign key (objet_id) references objet (id) on delete restrict on update restrict;
create index ix_fait_historique_objet_5 on fait_historique (objet_id);
alter table favori add constraint fk_favori_utilisateur_6 foreign key (utilisateur_id) references utilisateur (id) on delete restrict on update restrict;
create index ix_favori_utilisateur_6 on favori (utilisateur_id);
alter table favori add constraint fk_favori_objet_7 foreign key (objet_id) references objet (id) on delete restrict on update restrict;
create index ix_favori_objet_7 on favori (objet_id);
alter table historique add constraint fk_historique_utilisateur_8 foreign key (utilisateur_id) references utilisateur (id) on delete restrict on update restrict;
create index ix_historique_utilisateur_8 on historique (utilisateur_id);
alter table historique add constraint fk_historique_objet_9 foreign key (objet_id) references objet (id) on delete restrict on update restrict;
create index ix_historique_objet_9 on historique (objet_id);
alter table image add constraint fk_image_objet_10 foreign key (objet_id) references objet (id) on delete restrict on update restrict;
create index ix_image_objet_10 on image (objet_id);
alter table parcours_objet add constraint fk_parcours_objet_objet_11 foreign key (objet_id) references objet (id) on delete restrict on update restrict;
create index ix_parcours_objet_objet_11 on parcours_objet (objet_id);
alter table parcours_objet add constraint fk_parcours_objet_parcours_12 foreign key (parcours_id) references parcours (id) on delete restrict on update restrict;
create index ix_parcours_objet_parcours_12 on parcours_objet (parcours_id);
alter table proposition_modification add constraint fk_proposition_modification_utilisateur_13 foreign key (utilisateur_id) references utilisateur (id) on delete restrict on update restrict;
create index ix_proposition_modification_utilisateur_13 on proposition_modification (utilisateur_id);
alter table proposition_modification add constraint fk_proposition_modification_objet_14 foreign key (objet_id) references objet (id) on delete restrict on update restrict;
create index ix_proposition_modification_objet_14 on proposition_modification (objet_id);
alter table video add constraint fk_video_objet_15 foreign key (objet_id) references objet (id) on delete restrict on update restrict;
create index ix_video_objet_15 on video (objet_id);



alter table statistique_objet add constraint fk_statistique_objet_objet_01 foreign key (objet1) references objet (id) on delete restrict on update restrict;

alter table statistique_objet add constraint fk_statistique_objet_objet_02 foreign key (objet2) references objet (id) on delete restrict on update restrict;

alter table oeuvre_composite add constraint fk_oeuvre_composite_objet_01 foreign key (oeuvre_principale) references objet (id) on delete restrict on update restrict;

alter table oeuvre_composite add constraint fk_oeuvre_composite_objet_02 foreign key (oeuvre_inspiree) references objet (id) on delete restrict on update restrict;

# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table audio;

drop table commentaire;

drop table contenu_site;

drop table fait_historique;

drop table favori;

drop table feedback;

drop table historique;

drop table image;

drop table objet;

drop table statistique_objet;

drop table oeuvre_composite;

drop table parcours;

drop table parcours_objet;

drop table proposition_modification;

drop table utilisateur;

drop table video;

SET FOREIGN_KEY_CHECKS=1;

