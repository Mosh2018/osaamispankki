export interface Experience {
  userId: number;
  hide: boolean;
  id: number;
  company: string;
  position: string;
  description: string;
  startYear: Date;
  endYear: Date;
}

export function initExperience() {
  return {
    userId: 0,
    hide: false,
    id: 0,
    company: '',
    position: '',
    description: '',
    startYear: null,
    endYear: null
  };
}
