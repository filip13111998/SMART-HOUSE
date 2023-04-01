export interface RegisterCsrInterface{
  username: string;
  password: string;
  name: string,
  role:string,
  validityStart: number,
  validityPeriod:number,
  commonName: string,
  organizationUnion: string,
  organizationName: string,
  localityName: string,
  stateName: string,
  country: string,
}
