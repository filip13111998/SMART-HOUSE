export interface TableCertificate{
  id:string;
  validityStart:string;
  validityPeriod:string;
  issuer:string;
  subject:string;
  template:string;
  delete:boolean;
}
