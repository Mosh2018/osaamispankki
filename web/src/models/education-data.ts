export interface EducationData {
  userId: number;
  hide: boolean;
  id: number;
  nameOfInstitution: string;
  degree: string;
  location: string;
  startYear: Date;
  endYear: Date;
}

export function initEducationData() {
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
