export interface IProducto {
  id?: number;
  codigo?: string;
  descripcion?: string;
  imagenContentType?: string;
  imagen?: any;
  unidad?: string;
  cantidad?: number;
  precio?: number;
  iva?: number;
  icovalor?: number;
}

export const defaultValue: Readonly<IProducto> = {};
