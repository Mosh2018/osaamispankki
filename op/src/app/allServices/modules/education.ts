export interface Education {
  userId: number;
  hide: boolean;
  id: number;
  nameOfInstitution: string;
  degree: string;
  location: string;
  startYear: Date;
  endYear: Date;
}

export function initEducation() {
  return {
    userId: 0,
    hide: false,
    id: 0,
    nameOfInstitution: '',
    degree: '',
    location: '',
    startYear: null,
    endYear: null
  };
}
