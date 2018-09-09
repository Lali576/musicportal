import { NgModule } from '@angular/core';
import { RouterModule, Routes } from "@angular/router";

import { MainPageComponent } from "../sites/main-page/main-page.component";
import { AlbumListComponent } from "../sites/album/album-list/album-list.component";
import { AuthGuard } from "../auth.guard";
import { AlbumEditComponent } from "../sites/album/album-edit/album-edit.component";
import { AlbumDetailComponent } from "../sites/album/album-detail/album-detail.component";
import { SongListComponent } from "../sites/song/song-list/song-list.component";
import { SongEditComponent } from "../sites/song/song-edit/song-edit.component";
import { SongDetailComponent } from "../sites/song/song-detail/song-detail.component";
import { PlaylistListComponent } from "../sites/playlist/playlist-list/playlist-list.component";
import { PlaylistEditComponent } from "../sites/playlist/playlist-edit/playlist-edit.component";
import { PlaylistDetailComponent } from "../sites/playlist/playlist-detail/playlist-detail.component";
import { RegisterComponent } from "../sites/user/register/register.component";
import { LoginComponent } from "../sites/user/login/login.component";
import { UserDetailComponent } from "../sites/user/user-detail/user-detail.component";
import { SearchComponent } from "../sites/search/search.component";
import {UserPasswordComponent} from "../sites/user/user-password/user-password.component";
import {UserEmailComponent} from "../sites/user/user-email/user-email.component";
import {UserIconComponent} from "../sites/user/user-icon/user-icon.component";
import {UserBiographyComponent} from "../sites/user/user-biography/user-biography.component";
import {UserEditComponent} from "../sites/user/user-edit/user-edit.component";
import {AlbumCoverComponent} from "../sites/album/album-cover/album-cover.component";
import {AlbumFormComponent} from "../sites/album/album-form/album-form.component";
import {PlaylistFormComponent} from "../sites/playlist/playlist-form/playlist-form.component";

const routes: Routes = [
  {
    path: '',
    component: MainPageComponent
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
    path: 'user/:id/update/details',
    component: UserEditComponent,
    canActivate: [AuthGuard],
    data: {roles: ['USER', 'ARTIST']}
  },
  {
    path: 'user/:id/update/password',
    component: UserPasswordComponent,
    canActivate: [AuthGuard],
    data: {roles: ['USER', 'ARTIST']}
  },
  {
    path: 'user/:id/update/email',
    component: UserEmailComponent,
    canActivate: [AuthGuard],
    data: {roles: ['USER', 'ARTIST']}
  },
  {
    path: 'user/:id/update/icon',
    component: UserIconComponent,
    canActivate: [AuthGuard],
    data: {roles: ['USER', 'ARTIST']}
  },
  {
    path: 'user/:id/update/biography',
    component: UserBiographyComponent,
    canActivate: [AuthGuard],
    data: {roles: ['USER', 'ARTIST']}
  },
  {
    path: 'album/list',
    component: AlbumListComponent,
    canActivate: [AuthGuard],
    data: {roles: ['ARTIST']}
  },
  {
    path: 'album/new',
    component: AlbumFormComponent,
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
    path: 'album/:id/update/details',
    component: AlbumEditComponent,
    canActivate: [AuthGuard],
    data: {roles: ['ARTIST']}
  },
  {
    path: 'album/:id/update/cover',
    component: AlbumCoverComponent,
    canActivate: [AuthGuard],
    data: {roles: ['ARTIST']}
  },
  {
    path: 'song/list',
    component: SongListComponent,
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
    path: 'playlist/list',
    component: PlaylistListComponent,
    canActivate: [AuthGuard],
    data: {roles: ['ARTIST', 'USER']}
  },
  {
    path: 'playlist/new',
    component: PlaylistFormComponent,
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
    path: 'search/:word',
    component: SearchComponent,
  }
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  declarations: []
})
export class RoutingModule { }
