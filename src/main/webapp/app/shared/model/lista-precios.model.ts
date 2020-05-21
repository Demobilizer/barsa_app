export interface IListaPrecios {
  id?: number;
  descripcion?: string;
  porcentaje?: number;
  valor?: number;
}

export const defaultValue: Readonly<IListaPrecios> = {};
