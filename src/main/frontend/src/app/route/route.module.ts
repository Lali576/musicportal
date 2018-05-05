import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterModule, Routes} from "@angular/router";
import {MainPageComponent} from "../sites/main-page/main-page.component";
import {AlbumListComponent} from "../sites/album/album-list/album-list.component";
import {AuthGuard} from "../auth.guard";
import {AlbumEditComponent} from "../sites/album/album-edit/album-edit.component";
import {AlbumDetailComponent} from "../sites/album/album-detail/album-detail.component";
import {SongListComponent} from "../sites/song/song-list/song-list.component";
import {SongEditComponent} from "../sites/song/song-edit/song-edit.component";
import {SongDetailComponent} from "../sites/song/song-detail/song-detail.component";
import {PlaylistListComponent} from "../sites/playlist/playlist-list/playlist-list.component";
import {PlaylistEditComponent} from "../sites/playlist/playlist-edit/playlist-edit.component";
import {PlaylistService} from "../service/playlist.service";
import {PlaylistDetailComponent} from "../sites/playlist/playlist-detail/playlist-detail.component";
import {RegisterComponent} from "../sites/user/register/register.component";
import {LoginComponent} from "../sites/user/login/login.component";
import {UserDetailComponent} from "../sites/user/user-detail/user-detail.component";

const routes: Routes = [
  {
    path: '',
    redirectTo: '/dashboard',
    pathMatch: 'full'
  },
  {
    path: 'dashboard',
    component: MainPageComponent
  },
  {
    path: 'album',
    component: AlbumListComponent,
    canActivate: [AuthGuard],
    data: {roles: ['ARTIST']}
  },
  {
    path: 'album/new',
    component: AlbumEditComponent,
    canActivate: [AuthGuard],
    data: {roles: ['ARTIST']}
  },
  {
    path: 'album/:id',
    component: AlbumDetailComponent,
    canActivate: [AuthGuard],
    data: {roles: ['ARTIST', 'USER', 'GUEST']}
  },
  {
    path: 'album/:id/edit',
    component: AlbumEditComponent,
    canActivate: [AuthGuard],
    data: {roles: ['ARTIST']}
  },
  {
    path: 'song',
    component: SongListComponent,
    canActivate: [AuthGuard],
    data: {roles: ['ARTIST']}
  },
  {
    path: 'song/new',
    component: SongEditComponent,
    canActivate: [AuthGuard],
    data: {roles: ['ARTIST']}
  },
  {
    path: 'song/:id',
    component: SongDetailComponent,
    canActivate: [AuthGuard],
    data: {roles: ['ARTIST', 'USER', 'GUEST']}
  },
  {
    path: 'song/:id/edit',
    component: SongEditComponent,
    canActivate: [AuthGuard],
    data: {roles: ['ARTIST']}
  },
  {
    path: 'playlist',
    component: PlaylistListComponent,
    canActivate: [AuthGuard],
    data: {roles: ['ARTIST', 'USER']}
  },
  {
    path: 'playlist/new',
    component: PlaylistEditComponent,
    canActivate: [AuthGuard],
    data: {roles: ['ARTIST', 'USER']}
  },
  {
    path: 'playlist/:id',
    component: PlaylistDetailComponent,
    canActivate: [AuthGuard],
    data: {roles: ['ARTIST', 'USER', 'GUEST']}
  },
  {
    path: 'playlist/:id/edit',
    component: PlaylistEditComponent,
    canActivate: [AuthGuard],
    data: {roles: ['ARTIST', 'USER']}
  },
  {
    path: 'register',
    component: RegisterComponent
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'user/:id',
    component: UserDetailComponent,
    canActivate: [AuthGuard],
    data: {roles: ['USER', 'ARTIST']}
  },
  {
    path: 'user/:id/edit',
    component: UserDetailComponent,
    canActivate: [AuthGuard],
    data: {roles: ['USER', 'ARTIST']}
  }
]

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: []
})
export class RouteModule { }
