import { IPedidoCabecera } from 'app/shared/model/pedido-cabecera.model';

export interface ICliente {
  id?: number;
  tipoDoc?: string;
  noIdentificacion?: number;
  nombre?: string;
  apellido?: string;
  direccion?: string;
  telefono?: string;
  celular?: string;
  email?: string;
  pedidoCabeceras?: IPedidoCabecera[];
}

export const defaultValue: Readonly<ICliente> = {};
